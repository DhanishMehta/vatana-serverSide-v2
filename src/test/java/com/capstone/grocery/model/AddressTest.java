package com.capstone.grocery.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddressTest {
    
    Address address2 = new Address();
    Address address = new Address("test", "test", "test", "test", "test", "test", "test", "test");
    
    @Test
    void testBuilder() {
        Address newAddress = Address.builder().addressId("test").build();
        assertEquals(newAddress.getAddressId(), "test");
    }
    
    @Test
    void testConstructor() {
        assertEquals(address, address);
        assertEquals(address2, address2);
    }

    @Test
    void testGetAddressCity() {
        assertEquals(address.getAddressCity(), "test");
    }
    
    @Test
    void testGetAddressGeoHash() {
        assertEquals(address.getAddressGeoHash(), "test");
    }
    
    @Test
    void testGetAddressId() {
        assertEquals(address.getAddressId(), "test");
    }
    
    @Test
    void testGetAddressLandmark() {
        assertEquals(address.getAddressLandmark(), "test");
        assertEquals(address.getAddressLandmark(), "test");
    }
    
    @Test
    void testGetAddressLineOne() {
        assertEquals(address.getAddressLineOne(), "test");
    }
    
    @Test
    void testGetAddressLineTwo() {
        assertEquals(address.getAddressLineTwo(), "test");
    }
    
    @Test
    void testGetAddressPincode() {
        assertEquals(address.getAddressPincode(), "test");
        
    }
    
    @Test
    void testGetAddressState() {
        assertEquals(address.getAddressState(), "test");   
    }
    
    @Test
    void testSetAddressCity() {
        address.setAddressCity("test");
        assertEquals(address.getAddressCity(), "test");   
    }
    
    @Test
    void testSetAddressGeoHash() {
        address.setAddressGeoHash("test");
        assertEquals(address.getAddressGeoHash(), "test");   
    }

    @Test
    void testSetAddressId() {
        address.setAddressId("test");
        assertEquals(address.getAddressId(), "test");   
    }
    
    @Test
    void testSetAddressLandmark() {
        address.setAddressLandmark("test");
        assertEquals(address.getAddressLandmark(), "test");   
    }
    
    @Test
    void testSetAddressLineOne() {
        address.setAddressLineOne("test");
        assertEquals(address.getAddressLineOne(), "test");   
    }
    
    @Test
    void testSetAddressLineTwo() {
        address.setAddressLineTwo("test");
        assertEquals(address.getAddressLineTwo(), "test");   
    }
    
    @Test
    void testSetAddressPincode() {
        address.setAddressPincode("test");
        assertEquals(address.getAddressPincode(), "test");  
    }
    
    @Test
    void testSetAddressState() {
        address.setAddressState("test");
        assertEquals(address.getAddressState(), "test");  
    }

    @Test
    void testEquals() {
        assertEquals(address.equals(address), true);
    }

    @Test
    void testToString() {
        assertEquals(address.toString(),address.toString());
    }
    @Test
    void testHashCode() {
        assertEquals(address.hashCode(), address.hashCode());
    }
    @Test
    void testCanEqual() {
        assertEquals(address.canEqual(address), true);
    }
}
