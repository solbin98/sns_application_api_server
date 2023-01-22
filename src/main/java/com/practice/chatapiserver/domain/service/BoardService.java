package com.practice.chatapiserver.domain.service;

import com.practice.chatapiserver.ErrorCodeEnum;
import com.practice.chatapiserver.domain.dto.BoardDto;
import com.practice.chatapiserver.domain.dto.BoardWriteDto;
import com.practice.chatapiserver.domain.embeddable.TimeData;
import com.practice.chatapiserver.domain.entity.Board;
import com.practice.chatapiserver.domain.entity.File_Board;
import com.practice.chatapiserver.domain.entity.Likes;
import com.practice.chatapiserver.domain.entity.Member;
import com.practice.chatapiserver.domain.repository.BoardRepo;
import com.practice.chatapiserver.domain.repository.File_BoardRepo;
import com.practice.chatapiserver.domain.repository.LikesRepo;
import com.practice.chatapiserver.util.ImageUploadManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class BoardService {
    @Autowired
    private BoardRepo boardRepo;
    @Autowired
    private LikesRepo likesRepo;
    @Autowired
    private File_BoardRepo file_boardRepo;

    public List<BoardDto> getFriendBoardWithMemberIdAndPaging(Long member_id, int page){
        List<Board> boardList = boardRepo.getFriendsBoardWithMemberIdAndPaging(member_id, page);
        List<BoardDto> boardDtoList = new ArrayList<>();
        for(Board b : boardList) boardDtoList.add(new BoardDto(b));
        return boardDtoList;
    }

    public List<BoardDto> getBoardWithMemberIdAndPaging(Long member_id, int page){
        List<Board> boardList = boardRepo.getBoardWithMemberId(member_id, page);
        List<BoardDto> boardDtoList = new ArrayList<>();
        for(Board b : boardList) boardDtoList.add(new BoardDto(b));
        return boardDtoList;
    }

    public void addBoard(BoardWriteDto boardWriteDto, Long member_id){
        Board board = new Board();
        board.setMember(new Member(member_id));
        board.setContent(boardWriteDto.getContent());
        board.setTitle(boardWriteDto.getTitle());
        board.setTimeData(new TimeData());
        boardRepo.saveBoard(board);
        boardRepo.flush();
        boardRepo.clear();

        List<File_Board> file_boardList = file_boardRepo.findByBoardId(board.getId());
        for(File_Board fb : file_boardList) fb.setUsed(false); // 기존에 사용되던 게시판 이미지 파일들 used = false 로

        List<MultipartFile> list = boardWriteDto.getImageList();
        for(MultipartFile mf : list){ // 이미지들 저장해주기
            File_Board file_board = new File_Board();
            file_board.setUsed(true);
            file_board.setTimeData(new TimeData());
            file_board.setName(UUID.randomUUID().toString() + "_" + mf.getName());
            file_board.setPath(""); // 이 부분 s3 공부한 다음에 연동
            ImageUploadManager.saveImage(); // 이 부분도
            file_boardRepo.save(file_board);
        }
    }

    public void updateBoard(BoardDto boardDto, Long member_id) {
        if(boardDto.getMember_id() != member_id){ // 업데이트 요청을 보낸 사람이 본인인지 확인하기
            throw new IllegalArgumentException(ErrorCodeEnum.INVALID_ARGUMENT.getMsg());
        }

        Board board = new Board(boardDto.getBoard_id());
        board.setContent(boardDto.getContent());
        board.setTitle(boardDto.getTitle());

        TimeData timeData = new TimeData();
        timeData.setModified_at(LocalDateTime.now());
        board.setTimeData(timeData);

        boardRepo.updateBoard(board);
    }

    public void deleteBoard(Long board_id, Long member_id, Long request_member_id){
        if(request_member_id != member_id){ // 삭제 요청을 보낸 사람이 본인인지 확인하기
            throw new IllegalArgumentException(ErrorCodeEnum.INVALID_ARGUMENT.getMsg());
        }
        boardRepo.deleteById(board_id);
    }

    public void addLike(Long board_id, Long member_id){
        try{
            Likes likes = likesRepo.findByMemberIdAndBoardId(member_id, board_id);
            likesRepo.remove(likes);
        }
        catch (Exception e){
            Likes newLikes = new Likes();
            newLikes.setMember(new Member(member_id));
            newLikes.setBoard(new Board(board_id));
            likesRepo.saveLikes(newLikes);
        }
    }
}
