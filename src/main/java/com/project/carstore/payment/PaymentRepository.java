package com.project.carstore.payment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentDetails,Integer> {
}
