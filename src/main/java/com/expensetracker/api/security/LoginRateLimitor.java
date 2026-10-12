package com.expensetracker.api.security;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LoginRateLimitor{

    private static final int MAX_ATTEMPTS = 3;
    private static final int LOCK_MINUTES = 15;


    private final ConcurrentHashMap<String,Integer> attempts =
            new ConcurrentHashMap<>();

    private final ConcurrentHashMap<String,LocalDateTime> blockedUsers =
            new ConcurrentHashMap<>();

    public boolean isBlocked(String username){
        LocalDateTime blockUntill =blockedUsers.get(username);

     if(blockUntill == null){
         return false;
     }

     if(LocalDateTime.now().isBefore(blockUntill)){
         return true;
     }

     blockedUsers.remove(username , blockUntill);
     attempts.remove(username);

     return false;
    }


    public void loginFailed(String username) {
        int count = attempts.merge(username, 1, Integer::sum);

        if (count >= MAX_ATTEMPTS) {
            blockedUsers.put(
                    username,
                    LocalDateTime.now().plusMinutes(LOCK_MINUTES)
            );
        }
    }

    public void loginSucceeded(String username) {
        attempts.remove(username);
        blockedUsers.remove(username);
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