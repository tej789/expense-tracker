package com.expensetracker.api.security;
import com.expensetracker.api.model.LoginAttempt;
import com.expensetracker.api.repository.LoginAttemptRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class LoginRateLimitor{

    private static final int MAX_ATTEMPTS = 3;
    private static final int LOCK_MINUTES = 15;

    private final LoginAttemptRepository loginAttemptRepository;

    public LoginRateLimitor(
            LoginAttemptRepository loginAttemptRepository
    ){
        this.loginAttemptRepository = loginAttemptRepository;
    }

    public boolean isBlocked(String username){

        return loginAttemptRepository.findByUsername(username)
                .map(attempt -> attempt.getBlockedUntill() != null
                && attempt.getBlockedUntill()
                        .isAfter(LocalDateTime.now()))
                .orElse(false);
    }

    @Transactional
    public void loginFailed(String username) {
        LoginAttempt attempt = loginAttemptRepository
                .findByUsername(username)
                .orElseGet(()->
                {
                    LoginAttempt newAttempt = new LoginAttempt();
                    newAttempt.setUsername(username);
                    return newAttempt;
                });

        LocalDateTime now = LocalDateTime.now();

        if(attempt.getBlockedUntill() != null && !attempt.getBlockedUntill().isAfter(now)){
            attempt.setFailedAttempts(0);
            attempt.setBlockedUntill(null);
        }

        attempt.setFailedAttempts(attempt.getFailedAttempts()+1);
        attempt.setLastFailedAt(now);

        if(attempt.getFailedAttempts() >= MAX_ATTEMPTS){
            attempt.setBlockedUntill(now.plusMinutes(LOCK_MINUTES));
        }
        loginAttemptRepository.save(attempt);
    }

    @Transactional
    public void loginSucceeded(String username) {
    loginAttemptRepository.deleteByUsername(username);
    }
}




// Concurrent Hashmap
    /*
    A ConcurrentHashMap is a high-performance, thread-safe version of HashMap in Java.
    Use it when multiple threads (users/requests) are reading and writing to the same map at
    the exact same time.

The Problem it Solves

• If you use a regular HashMap with multiple threads, your data will break (ConcurrentModificationException or data corruption).
• If you use older tools like Hashtable, the entire map locks up. Threads must wait in line, making your application slow.

How it Works (The Magic)

Instead of locking the entire map, it uses Bucket/Segment Locking:
• It splits the map into smaller parts (buckets).
• If Thread A is writing to Bucket 1, only Bucket 1 is locked.
• Thread B can write to Bucket 2 at the exact same time without waiting.
• Reading data (.get()) usually requires no locks at all, making it lightning-fast.



     */