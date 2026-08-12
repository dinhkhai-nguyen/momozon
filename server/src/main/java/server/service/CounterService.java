package server.service;

import org.springframework.stereotype.Service;

@Service
public class CounterService {
    private int count = 0;
    public int getAddIncrease() {
        return count++;
    }
}
