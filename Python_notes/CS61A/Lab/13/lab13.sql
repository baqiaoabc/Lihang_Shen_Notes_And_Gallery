.read data.sql


CREATE TABLE bluedog AS
  SELECT color,pet from students where color = "blue" and pet = "dog";

CREATE TABLE bluedog_songs AS
  SELECT color,pet,song from students where color = "blue" and pet = "dog";


CREATE TABLE matchmaker AS
  SELECT A.pet, A.song, A.color, B.color FROM students A, students B WHERE
  A.time < B.time AND A.pet == B.pet AND A.song == B.song;


CREATE TABLE sevens AS
  SELECT s.seven FROM students s JOIN numbers n ON s.time = n.time 
  WHERE s.number = 7 and n."7" = "True";


CREATE TABLE favpets AS
  SELECT pet, count(pet) from students group by pet order by count(pet) desc limit 10;


CREATE TABLE dog AS
  SELECT * from favpets where pet = "dog";


CREATE TABLE bluedog_agg AS
  SELECT song, count(song) from bluedog_songs group by song order by count(song) desc;


CREATE TABLE instructor_obedience AS
  SELECT seven, instructor, count(seven) from students where seven = "7" group by instructor;

