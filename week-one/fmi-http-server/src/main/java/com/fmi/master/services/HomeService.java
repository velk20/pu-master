package main.java.com.fmi.master.services;

import com.fmi.master.steriotypes.Injectable;

@Injectable
public class HomeService {
    public String getIndexHome() {
        return "home service";
    }
}
