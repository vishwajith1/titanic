package com.egen.titanic.service;

import com.egen.titanic.entity.Address;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface AddressService {
    public Optional<Address> getById(Integer id);

    public Address saveOrUpdate(Address address);

    public void deleteById(Integer id);
}
