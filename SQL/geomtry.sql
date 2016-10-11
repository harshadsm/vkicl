use vkicl;
CREATE TABLE geom (g GEOMETRY);
INSERT INTO geom VALUES (ST_GeomFromText('POINT(1 1)'));
select * from geom;
truncate table geom;
insert INTO geom VALUES (ST_GeomFromText('POLYGON((5.0 5.0,7.7 5.5,7 7,5 7, 5 5))'));
insert INTO geom VALUES (ST_GeomFromText('POLYGON((0 0,10 0,10 10,0 10,0 0))'));
insert INTO geom VALUES (ST_GeomFromText('POLYGON((0 0,2 0,2 2,0 2,0 0))'));
insert INTO geom VALUES (ST_GeomFromText('POLYGON((0 0,8 0,8 2,6 2,6 4,2 6,2 8,0 8,0 0))'));

insert INTO geom VALUES (ST_GeomFromText('POLYGON((150.0 10.0,150.0 150.0,10.0 150.0,10.0 300.0,300.0 300.0,300.0 10.0,150.0 10.0))'));

commit;
SELECT ST_AsText(g) as gg FROM geom;

SELECT ST_AsText(g) as gg,ST_Area(g) as area FROM geom;
SELECT ST_AsText(g) as gg,ST_Area(g) as area FROM geom where ST_Area(g) = 40;
