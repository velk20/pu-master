package main.java.com.fmi.master.services;

import com.fmi.master.steriotypes.Autowired;
import com.fmi.master.steriotypes.Injectable;

@Injectable
public class CustomerService {

    @Autowired
    private NestedLevel1 nestedLevel1;

    public String hello() {
        return "Hello World";
    }

    public String nested() {
        return this.nestedLevel1.nested();
    }

}
