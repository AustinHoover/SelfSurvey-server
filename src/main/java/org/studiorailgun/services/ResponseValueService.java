package org.studiorailgun.services;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.studiorailgun.dtos.response.ResponseRepository;
import org.studiorailgun.dtos.response.ResponseValue;
import org.studiorailgun.dtos.response.Response;
import org.studiorailgun.dtos.response.ResponseValuesRepository;

@Service
@Qualifier("ResponseValueService")
public class ResponseValueService {

    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private ResponseValuesRepository responseValuesRepository;

    Semaphore databaseLock = new Semaphore(1);

    ExecutorService threadPool;

    public ResponseValueService(){
        threadPool = Executors.newFixedThreadPool(1);
    }

    @Transactional
    public void queueResponseValue(ResponseValue value, int responseId) throws Exception {
        System.out.println("=" + responseId);
        threadPool.submit(() -> {
            databaseLock.acquireUninterruptibly();
            ResponseValue newValue = responseValuesRepository.save(value);
            System.out.println("==" + responseId);
            responseRepository.findById(responseId).ifPresentOrElse(
            (response) -> {
                System.out.println("===" + responseId);
                try {
                    System.out.println("set response");
                    newValue.setResponse(response);
                    System.out.println("set response value");
                    response.addResponseValue(newValue);
                    System.out.println("save 1");
                    responseRepository.save(response);
                    System.out.println("save 2");
                    responseValuesRepository.save(newValue);
                    databaseLock.release();
                } catch (Exception ex){
                    ex.printStackTrace();
                    databaseLock.release();
                }
                System.out.println(newValue.getResponse().getId() + "~");
                databaseLock.release();
            }, 
            () -> {
                System.out.println(responseId + " not found");
                databaseLock.release();
            });
        });
    }
    
}
