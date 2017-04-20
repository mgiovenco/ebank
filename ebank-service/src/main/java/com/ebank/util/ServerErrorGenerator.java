package com.ebank.util;

import com.ebank.exception.InternalServerErrorException;
import com.ebank.exception.ServiceUnavailableException;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ServerErrorGenerator {

    private final Random randomNum = new Random();

    /**
     * Throws a random server error 1/10 times.
     * Note: 500 or 503 seem like the most likely service errors that could
     * still most likely be reachable upon a subsequent service call.
     * @return
     */
   public void generateRandomServerError() {
       if(randomNum.nextInt(10) == 1){
           if(randomNum.nextInt(2) == 0) {
               // returns 500
               throw new InternalServerErrorException("Internal Server Error Exception");
           } else{
               // return 503
               throw new ServiceUnavailableException("Service Unavailable Exception");
           }
       }
   }
}
