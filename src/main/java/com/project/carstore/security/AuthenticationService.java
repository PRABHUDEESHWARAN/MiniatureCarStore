package com.project.carstore.security;

import com.project.carstore.cart.CartException;
import com.project.carstore.customer.Customer;
import com.project.carstore.customer.CustomerDto;
import com.project.carstore.customer.CustomerRepository;
import com.project.carstore.customer.CustomerService;
import com.project.carstore.exceptions.AuthenticationException;
import com.project.carstore.exceptions.CustomerException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;


    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,CustomerRepository customerRepository, AuthenticationManager authenticationManager, CustomerService customerService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.customerService = customerService;
        this.customerRepository=customerRepository;
    }

    public String register(UserDTO request) throws CustomerException, CartException {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.valueOf(request.getRole()));
        user = userRepository.save(user);
        if (request.getRole().equals("USER")) {
            CustomerDto customerDto = new CustomerDto(user.getId(), request.getFirstName(), request.getLastName(), request.getEmail(), request.getPassword(), request.getMobileNo());
            this.customerService.addCustomerToDb(customerDto);
        }
        return "success";
    }

    public AuthenticationResponse authenticate(User request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);
        String role = user.getRole().toString();
        return new AuthenticationResponse(token, role);

    }

    public ValidateDTO isValidToken(String token) {
        Optional<User> user = userRepository.findByUsername(jwtService.extractUsername(token));
        if (user.isPresent()) {
            return new ValidateDTO(jwtService.isValid(token, user.get()),user.get().getRole().toString());
        }else return new ValidateDTO(false,null);

    }

    public Profile getUserProfile(String token) throws AuthenticationException {
        Optional<Profile> userProfile;
        Optional<User> userOpt = userRepository.findByUsername(jwtService.extractUsername(token));
        if(userOpt.isPresent() && jwtService.isValid(token, userOpt.get())){
            User user=userOpt.get();
            Optional<Customer> customerOpt=this.customerRepository.findCustomerByUserId(user.getId());
            if(customerOpt.isPresent()){
                Customer customer=customerOpt.get();
                return new Profile(user.getId(),customer.getId(),customer.getCartId(), user.getUsername(), user.getFirstName(), user.getLastName(),customer.getMobileNo(), customer.getEmail(),user.getRole().toString(),customer.getAddress());
            }else throw new AuthenticationException("Customer not Found"+user.getId());
        }else throw new AuthenticationException("User not Found / token Expired");
    }
}
