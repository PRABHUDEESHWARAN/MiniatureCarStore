package com.project.carstore.product;
import com.project.carstore.exceptions.ProductException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService {
    static final String ISSUE ="No Products Found";

    private final ProductRepository productRepository;
    public ProductServiceImp(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product addProductToDb(ProductDTO product) throws ProductException {
        //handle exception here
        if(product==null || product.getName()==null || product.getPrice()==null || product.getColour()==null || product.getQuantity()==null)
        {
            throw new ProductException("Insufficient Product Details");
        }
        if(Boolean.TRUE.equals(this.productRepository.existsByNameIgnoreCase(product.getName())))
        {
            throw new ProductException("Product already exists with name:"+product.getName());
        }
        Product productToBeAdded =new Product( product.getName(), product.getPrice(), product.getDescription(), product.getImageUrl(), product.getColour(), product.getQuantity());
        return this.productRepository.save(productToBeAdded);
    }

    @Override
    public Product deleteProductFromDb(Long id) throws ProductException {
        //handle exception
        if(id==null)
        {
            throw new ProductException("Invalid product id");
        }
        // get product from db, if available delete it
        Optional<Product> foundProduct=productRepository.findById(id);
        if(foundProduct.isEmpty())
        {
            throw new ProductException("No such product exist with Id:"+ id);
        }
        this.productRepository.delete(foundProduct.get());
        return foundProduct.get();
    }

    @Override
    public Product updateProductInDb(UpdateProductDTO productDetails) throws ProductException {
        //handle exceptions
        if(productDetails==null) throw new ProductException("ProductDetails cannot be Null");
        Long productId= productDetails.getProductId();
        if(productId==null)
        {
            throw new ProductException("Product ID cannot be null");
        }
        Optional<Product> findProduct=this.productRepository.findById(productId);
        if(findProduct.isPresent())
        {
            //set color
            if(productDetails.getColour()!=null)
            {
                findProduct.get().setColour(productDetails.getColour());
            }
            //set price
            if(productDetails.getPrice()!=null)
            {
                findProduct.get().setPrice(productDetails.getPrice());
            }
            //set des
            if(productDetails.getDescription()!=null)
            {
                findProduct.get().setDescription(productDetails.getDescription());
            }
            //set qty
            if(productDetails.getQuantity()!=null)
            {
                findProduct.get().setQuantity(productDetails.getQuantity());
            }
            //set url
            if(productDetails.getImageUrl()!=null)
            {
                findProduct.get().setImageUrl(productDetails.getImageUrl());
            }
            return this.productRepository.save(findProduct.get());
        }else {
            throw new ProductException("No product exist with Id:"+productDetails.getProductId());
        }
    }

    @Override
    public List<Product> getAllProducts() throws ProductException {
        if(this.productRepository.findAll().isEmpty())
            throw new ProductException(ISSUE);
        return this.productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) throws ProductException {
        //handle exceptions
        Optional<Product> foundProduct=this.productRepository.findById(id);
        if(foundProduct.isEmpty())
            throw new ProductException("Product not found:"+id);

        return foundProduct.get();
    }

    @Override
    public List<Product> getAllProductsByPrice(Double price) throws ProductException {
        if(this.productRepository.findAll().isEmpty())
            throw new ProductException(ISSUE);
        return productRepository.findByPrice(price);
    }

    @Override
    public List<Product> getAllProductsByPriceRange(Double min, Double max) throws ProductException {
        if(this.productRepository.findAll().isEmpty())
            throw new ProductException(ISSUE);
        List<Product> allProducts=productRepository.findAll();
        List<Product> productsPriceRange;
        productsPriceRange = allProducts.stream().filter(product->product.getPrice() >=min && product.getPrice() <=max).toList();
        return productsPriceRange;
    }

    @Override
    public List<Product> getAllProductsSortedByPrice( ) throws ProductException {
        if(this.productRepository.findAll().isEmpty())
            throw new ProductException(ISSUE);
        return productRepository.findAllByOrderByPriceAsc();
    }

    @Override
    public List<Product> getAllProductsByName(String name) throws ProductException {
        if(this.productRepository.findAll().isEmpty())
            throw new ProductException(ISSUE);
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Product> getAllProductsByDescription(String description) throws ProductException {
        if(this.productRepository.findAll().isEmpty())
            throw new ProductException(ISSUE);
        return productRepository.findByDescriptionContainingIgnoreCase(description);
    }
}
