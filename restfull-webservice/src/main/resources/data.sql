

/*
CREATE TRIGGER before_insert_user
  BEFORE INSERT ON `user`
  FOR EACH ROW
  SET new.id = uuid();
  // cause problem in in post method in api  because it generated uuid different that what been actually saved in db
*/

INSERT INTO `user` (`id`,`first_name`,`last_name`,`username`,`email`,`date_created`) VALUES (UUID(),'John','Smith','john014','johnsm@gmail.com',CURRENT_TIMESTAMP);
INSERT INTO `user` (`id`,`first_name`,`last_name`,`username`,`email`,`date_created`) VALUES (UUID(),'Sarah','Jackson','jack478','sarah-jackson78@gmail.com',CURRENT_TIMESTAMP);


INSERT INTO `usersapi` (`id`, `email`, `password`, `username`,`roles`) 
VALUES ('1', 'admin@gmail.com', '$2a$10$o6waKda/zukybLm9dIur3uTZRxByKGSRQqeewKF5SMJ6mLLLdA/eC', 'admin','ROLE_ADMIN,ROLE_USER');


INSERT INTO `usersapi` (`id`, `email`, `password`, `username`,`roles`) 
VALUES ('2', 'user@gmail.com', '$2a$10$iUOXghHUg1Ry5za4e4F24OulZmqTtxQ87OISPkv3CiEJTNEz50bXm', 'user','ROLE_USER');
