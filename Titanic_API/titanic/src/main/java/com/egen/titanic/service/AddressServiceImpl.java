package com.egen.titanic.service;

import com.egen.titanic.entity.Address;
import com.egen.titanic.service.AddressService;
import com.egen.titanic.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Override
    public Optional<Address> getById(Integer id) {
        return addressRepository.findById(id);
    }

    @Override
    public Address saveOrUpdate(Address address) {
        return addressRepository.saveAndFlush(address);
    }

    @Override
    public void deleteById(Integer id) {
        addressRepository.deleteById(id);
    }
}
