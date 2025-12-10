# Memcached Example with Professor Service

This example demonstrates how to use Memcached for caching with the Professor entity in a Spring Boot application.

## Prerequisites

1. **Install and run Memcached**:
   ```bash
   # On Ubuntu/Debian
   sudo apt-get install memcached
   sudo systemctl start memcached
   
   # On macOS
   brew install memcached
   brew services start memcached
   
   # Using Docker
   docker run -d -p 11211:11211 --name memcached memcached
   ```

2. **Verify Memcached is running**:
   ```bash
   telnet localhost 11211
   # or
   nc -zv localhost 11211
   ```

## How It Works

### Caching Annotations

1. **@Cacheable**: Retrieves data from cache if available, otherwise executes the method and caches the result
   - Used in `getProfessorById()` and `getAllProfessors()`
   - Cache key format: `professor::{id}` or `professors::all`

2. **@CachePut**: Always executes the method and updates the cache
   - Used in `updateProfessor()`

3. **@CacheEvict**: Removes entries from cache
   - Used in `deleteProfessor()` and `clearCache()`

### Testing the Cache

1. **Start the application**:
   ```bash
   mvn spring-boot:run
   ```

2. **Test GET endpoint (observe caching)**:
   ```bash
   # First call - slow (fetches from "database")
   curl http://localhost:8083/api/professors/1
   
   # Second call - fast (fetches from Memcached)
   curl http://localhost:8083/api/professors/1
   ```

3. **Test GET all professors**:
   ```bash
   curl http://localhost:8083/api/professors
   ```

4. **Create a new professor**:
   ```bash
   curl -X POST http://localhost:8083/api/professors \
     -H "Content-Type: application/json" \
     -d '{
       "name": "Dr. Maria Garcia",
       "department": "Biology",
       "email": "maria.garcia@unsis.edu"
     }'
   ```

5. **Update a professor** (updates cache):
   ```bash
   curl -X PUT http://localhost:8083/api/professors/1 \
     -H "Content-Type: application/json" \
     -d '{
       "name": "Dr. John Smith Updated",
       "department": "Computer Science",
       "email": "john.smith.updated@unsis.edu"
     }'
   ```

6. **Delete a professor** (evicts cache):
   ```bash
   curl -X DELETE http://localhost:8083/api/professors/1
   ```

7. **Clear all caches**:
   ```bash
   curl -X POST http://localhost:8083/api/professors/cache/clear
   ```

## Observing Cache Behavior

Watch the application logs to see when data is fetched from the database vs. cache:

- **Cache MISS**: You'll see log message "Fetching professor with id X from database (cache miss)"
- **Cache HIT**: No log message (data returned from Memcached)

The simulated database delay (1 second) makes it easy to notice the performance difference:
- First request: ~1 second
- Cached requests: ~10ms

## Cache Configuration

- **Cache name**: `default`
- **TTL**: 3600 seconds (1 hour)
- **Server**: localhost:11211
- **Consistent hashing**: Enabled

## Key Features Demonstrated

1. ✅ Basic caching with `@Cacheable`
2. ✅ Cache updates with `@CachePut`
3. ✅ Cache eviction with `@CacheEvict`
4. ✅ Custom cache keys
5. ✅ Serializable entities
6. ✅ Cache invalidation strategies
7. ✅ Performance improvement visualization

## Monitoring Memcached

Connect to Memcached and check statistics:

```bash
telnet localhost 11211
stats
stats items
stats cachedump 1 0
quit
```

Or use memcached-tool:
```bash
memcached-tool localhost:11211 stats
```
