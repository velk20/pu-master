package main.java.com.fmi.master.services;

import com.fmi.master.steriotypes.Injectable;

@Injectable
public class CustomerService {
    public String hello() {
        return "Hello World";
    }
}
