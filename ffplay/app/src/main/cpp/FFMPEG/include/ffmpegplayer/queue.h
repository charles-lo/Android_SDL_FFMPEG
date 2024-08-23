//
// Created by charles on 2024/8/17.
//
#ifndef FFMPEG_PLAYER_QUEUE_H
#define FFMPEG_PLAYER_QUEUE_H

#include <sys/types.h>
#include <pthread.h>

extern "C" {
#include "libavformat/avformat.h"
}

#define QUEUE_MAX_SIZE 50

typedef AVPacket* NodeElement;

typedef struct _Node {
    NodeElement data;
    struct _Node* next;
} Node;

typedef struct _Queue {
    int size;
    Node* head;
    Node* tail;
    bool is_block;
    pthread_mutex_t* mutex_id;
    pthread_cond_t* not_empty_condition;
    pthread_cond_t* not_full_condition;
} Queue;

/**
 * @param queue
 */
void queue_init(Queue* queue);

/**
 * @param queue
 */
void queue_destroy(Queue* queue);

/**
 * @param queue
 * @return
 */
bool queue_is_empty(Queue* queue);

/**
 * @param queue
 * @return
 */
bool queue_is_full(Queue* queue);

/**
 * @param queue
 * @param element
 * @param tid
 * @param cid
 */
void queue_in(Queue* queue, NodeElement element);

/**
 * @param queue
 * @param tid
 * @param cid
 * @return
 */
NodeElement queue_out(Queue* queue);

/**
 * @param queue
 */
void queue_clear(Queue* queue);

/**
 * @param queue
 */
void break_block(Queue* queue);

#endif //FFMPEG_PLAYER_QUEUE_H