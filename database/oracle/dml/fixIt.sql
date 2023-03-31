SELECT * FROM movie WHERE myrating LIKE '%Â%';

update MOVIE SET MYRATING = '***½' WHERE MYRATING = '***Â½';
update MOVIE SET MYRATING = '**½' WHERE MYRATING = '**Â½';
update MOVIE SET MYRATING = '*½' WHERE MYRATING = '*Â½';
