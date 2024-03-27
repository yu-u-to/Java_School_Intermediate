package com.example.javasamplepj.domain.service.user;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.javasamplepj.domain.mapper.UserMapper;
import com.example.javasamplepj.domain.model.user.User;
import com.example.javasamplepj.domain.model.user.UserRequest;
import com.example.javasamplepj.domain.repository.UserRepository;
import com.example.javasamplepj.domain.util.user.PasswordUtil;

/**
 * ユーザー情報 Service
 */
// @RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {

  UserMapper mapper;

  /**
   * ユーザー情報 Repository
   */
  @Autowired
  private UserRepository userRepository;

  /**
   * ユーザー情報 全検索
   * 
   * @return 検索結果
   */
  public List<User> searchAll() {
    return userRepository.findAll();
  }

/**
   * ユーザ登録する情報のDBインサート処理
   * 
   * @param RequestUserRegist ユーザ登録APIのリクエストボディ
   */
  public void insertUser(UserRequest userRequest) {
    User user = new User();
    user = createUser(userRequest);
    userRepository.create(user);
  };

  /**
   * ユーザ登録するユーザ情報の作成処理
   * 
   * @param userRequest ユーザ登録APIのリクエストボディ
   * @return user ユーザ情報
   */
  private User createUser(UserRequest userRequest) {
    String hashPw;
    Date now = new Date();
    User user = new User();
    hashPw = PasswordUtil.hashSHA256(userRequest.getPassword());
    user.setUserName(userRequest.getUserName());
    user.setPassword(hashPw);
    user.setMailAddress(userRequest.getMailAddress());
    user.setCreateDate(now);
    user.setUpdateDate(now);
    return user;
  };
}