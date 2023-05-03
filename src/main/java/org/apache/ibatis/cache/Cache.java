/**
 *    Copyright 2009-2015 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.cache;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * SPI for cache providers.
 * 
 * One instance of cache will be created for each namespace.
 * 
 * The cache implementation must have a constructor that receives the cache id as an String parameter.
 * 
 * MyBatis will pass the namespace as id to the constructor.
 * 
 * <pre>
 * public MyCache(final String id) {
 *  if (id == null) {
 *    throw new IllegalArgumentException("Cache instances require an ID");
 *  }
 *  this.id = id;
 *  initialize();
 * }
 * </pre>
 *
 * @author Clinton Begin
 */

/**
 * FIFOCache：先进先出算法 回收策略，装饰类，内部维护了一个队列，来保证FIFO，一旦超出指定的大小，则从队列中获取Key并从被包装的Cache中移除该键值对
 * LoggingCache：输出缓存命中的日志信息,如果开启了DEBUG模式，则会输出命中率日志
 * LruCache：最近最少使用算法，缓存回收策略,在内部保存一个LinkedHashMap
 * ScheduledCache：定时清空Cache，但是并没有开始一个定时任务，而是在使用Cache的时候，才去检查时间是否到了
 * SerializedCache：序列化功能，将值序列化后存到缓存中。该功能用于缓存返回一份实例的Copy，用于保存线程安全
 * SoftCache：基于软引用实现的缓存管理策略,软引用回收策略，软引用只有当内存不足时才会被垃圾收集器回收
 * SynchronizedCache：同步的缓存装饰器，用于防止多线程并发访问
 * PerpetualCache 永久缓存，一旦存入就一直保持，内部就是一个HashMap
 * WeakCache：基于弱引用实现的缓存管理策略
 * TransactionalCache 事务缓存，一次性存入多个缓存，移除多个缓存
 * BlockingCache 可阻塞的缓存,内部实现是ConcurrentHashMap
 */
public interface Cache {

  /**
   * @return The identifier of this cache
   */
  String getId();

  /**
   * @param key Can be any object but usually it is a {@link CacheKey}
   * @param value The result of a select.
   */
  void putObject(Object key, Object value);

  /**
   * @param key The key
   * @return The object stored in the cache.
   */
  Object getObject(Object key);

  /**
   * As of 3.3.0 this method is only called during a rollback 
   * for any previous value that was missing in the cache.
   * This lets any blocking cache to release the lock that 
   * may have previously put on the key.
   * A blocking cache puts a lock when a value is null 
   * and releases it when the value is back again.
   * This way other threads will wait for the value to be 
   * available instead of hitting the database.
   *
   * 
   * @param key The key
   * @return Not used
   */
  Object removeObject(Object key);

  /**
   * Clears this cache instance
   */  
  void clear();

  /**
   * Optional. This method is not called by the core.
   * 
   * @return The number of elements stored in the cache (not its capacity).
   */
  int getSize();
  
  /** 
   * Optional. As of 3.2.6 this method is no longer called by the core.
   *  
   * Any locking needed by the cache must be provided internally by the cache provider.
   * 
   * @return A ReadWriteLock 
   */
  ReadWriteLock getReadWriteLock();

}