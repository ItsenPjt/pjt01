package com.newcen.newcen.comment.service;

import com.newcen.newcen.notice.repository.NoticeRepository;
import com.newcen.newcen.question.repository.QuestionsRepository;
import com.newcen.newcen.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
    //공용

    private final UserRepository userRepository;

    private final NoticeRepository noticeRepository;

    private final QuestionsRepository questionsRepository;

    //댓글 생성
    public void createComment(){

    }
    //댓글 수정

    //댓글 삭제


}
