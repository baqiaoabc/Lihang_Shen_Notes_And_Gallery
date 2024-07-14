CREATE TABLE parents AS
  SELECT "abraham" AS parent, "barack" AS child UNION
  SELECT "abraham"          , "clinton"         UNION
  SELECT "delano"           , "herbert"         UNION
  SELECT "fillmore"         , "abraham"         UNION
  SELECT "fillmore"         , "delano"          UNION
  SELECT "fillmore"         , "grover"          UNION
  SELECT "eisenhower"       , "fillmore";

CREATE TABLE dogs AS
  SELECT "abraham" AS name, "long" AS fur, 26 AS height UNION
  SELECT "barack"         , "short"      , 52           UNION
  SELECT "clinton"        , "long"       , 47           UNION
  SELECT "delano"         , "long"       , 46           UNION
  SELECT "eisenhower"     , "short"      , 35           UNION
  SELECT "fillmore"       , "curly"      , 32           UNION
  SELECT "grover"         , "short"      , 28           UNION
  SELECT "herbert"        , "curly"      , 31;

CREATE TABLE sizes AS
  SELECT "toy" AS size, 24 AS min, 28 AS max UNION
  SELECT "mini"       , 28       , 35        UNION
  SELECT "medium"     , 35       , 45        UNION
  SELECT "standard"   , 45       , 60;


-- Q1 The size of each dog
CREATE TABLE size_of_dogs AS
  SELECT d.name,s.size FROM dogs d, sizes s where d.height <= s.max and d.height > s.min;


-- Q2 All dogs with parents ordered by decreasing height of their parent
CREATE TABLE by_parent_height AS
  SELECT p.child FROM parents p LEFT JOIN dogs d on p.parent = d.name
  ORDER BY d.height desc;

-- Q3 Filling out this helper table is optional
-- 这里的trick是通过”<”来过滤掉因为cross join而重复的case
-- 即类似于 barack clinton 和 clinton barack这样的数据只出现一次
CREATE TABLE siblings AS
  select t1.child as dog1, t2.child as dog2, t2.size as common_size
  from 
  (SELECT p.parent parent,p.child child,s.size size 
   FROM parents p LEFT JOIN size_of_dogs s 
   on p.child = s.name) t1,
  (SELECT p.parent parent,p.child child,s.size size 
  FROM parents p LEFT JOIN size_of_dogs s 
  on p.child = s.name) t2
  where t1.size = t2.size and t1.child < t2.child and t1.parent = t2.parent;

-- Q4 Ways to stack 4 dogs to a height of at least 170, ordered by total height
CREATE TABLE stacks_helper1(dog, stack_height, last_height);
CREATE TABLE stacks_helper2(dog1, dog2, stack_height, last_height);
CREATE TABLE stacks_helper3(dog1, dog2, dog3, stack_height, last_height);
CREATE TABLE stacks_helper4(dog1, dog2, dog3, dog4, stack_height, last_height);

-- Add your INSERT INTOs here
INSERT INTO stacks_helper1 
  SELECT 
      name, 
      height, 
      height
  FROM dogs;

INSERT INTO stacks_helper2
  SELECT 
      t1.dog, 
      t2.dog, 
      t1.stack_height + t2.stack_height, 
      t2.stack_height
  FROM 
      stacks_helper1 t1, 
      stacks_helper1 t2
  WHERE t1.dog <> t2.dog 
  -- 这里这个restraint可以消除所有重复的数据，因为所有dog的身高都不一样
  -- 如果有一样身高的dog，则消除不了，比如dog a和dog b都是20，那么就既会出现 a b 又会出现 b a
  -- 带等号的目的是为了保证每个情况都出现
  -- 想象一个height asc排序的table；
  AND t1.last_height <= t2.last_height
  ORDER BY t1.stack_height + t2.stack_height;

INSERT INTO stacks_helper3
  SELECT 
      t1.dog1, 
      t1.dog2, 
      t2.dog, 
      t1.stack_height + t2.stack_height, 
      t2.stack_height
  FROM 
      stacks_helper2 t1, 
      stacks_helper1 t2
  WHERE t1.dog1 <> t2.dog
  AND t1.dog2 <> t2.dog
  AND t1.last_height <= t2.last_height
  ORDER BY t1.stack_height + t2.stack_height;

INSERT INTO stacks_helper4
  SELECT 
      t1.dog1, 
      t1.dog2, 
      t1.dog3,
      t2.dog, 
      t1.stack_height + t2.stack_height, 
      t2.stack_height
  FROM 
      stacks_helper3 t1, 
      stacks_helper1 t2
  WHERE t1.dog1 <> t2.dog
  AND t1.dog2 <> t2.dog
  AND t1.dog3 <> t2.dog
  AND t1.last_height <= t2.last_height
  ORDER BY t1.stack_height + t2.stack_height;

CREATE TABLE stacks AS
  SELECT dog1 || ", " || dog2 || ", " || dog3 || ", " || dog4, stack_height
  FROM stacks_helper4 
  WHERE stack_height >= 170
  ORDER BY stack_height;