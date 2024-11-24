package main.java.com.fmi.master.services;

import com.fmi.master.steriotypes.Autowired;
import com.fmi.master.steriotypes.Injectable;

@Injectable
public class NestedLevel1 {
    @Autowired
    private NestedLevel2 nestedLevel2;

    public String nested() {
        return this.nestedLevel2.nested();
    }
}
