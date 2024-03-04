
/*
CREATE TRIGGER before_insert_user
  BEFORE INSERT ON `user`
  FOR EACH ROW
  SET new.id = uuid();
  // cause problem in in post method in api  because it generated uuid different that what been actually saved in db
*/

INSERT INTO `user` (`id`,`first_name`,`last_name`,`username`,`email`,`date_created`) VALUES (UUID(),'John','Smith','john014','johnsm@gmail.com',CURRENT_TIMESTAMP);
INSERT INTO `user` (`id`,`first_name`,`last_name`,`username`,`email`,`date_created`) VALUES (UUID(),'Sarah','Jackson','jack478','sarah-jackson78@gmail.com',CURRENT_TIMESTAMP);


