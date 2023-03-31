create table Video 
    (videoId number(8) NOT NULL,
     title varchar2(100) NOT NULL,
     videoType varchar2(15) NOT NULL,
     yearOfRelease number(4),
     rating varchar2(6),
     label varchar2(40),
     cost number(5,2),
     obtainedFrom varchar2(30),
     obtainedType varchar2(30),  
     region varchar2(10)
     oopYn number(1),  
     slipcoverYn number(1),
     caseType varchar2(20),  
     discCount number(2),
     spineNumber varchar2(7),
     location varchar2(30),	 
     comments varchar2(4000),
     totalMinutes number(4), 
     lastModified DATE);

comment on table Video is 'Shawn McKenna''s Video Collection.';
comment on column Video.videoId is 'The primary key for the video table.';
comment on column Video.title is 'The title of the video.';
comment on column Video.videoType is 'The type of media for the video.  A DVD, BD or VHS etc...';
comment on column Video.yearOfRelease is 'The year the video is released.  It is the date printed of the video.';
comment on column Video.rating is 'The rating I gave the video.  *, **, **1/2, **** etc.  I use the four-star system.';
comment on column Video.label is 'The label that produced this video (on the DVD, VHS, etc...)';
comment on column Video.cost is 'The cost of the video (purchase price.)';
comment on column Video.obtainedFrom is 'The retailer, person or place you obtained (bought, gift) this from.';
comment on column Video.obtainedType is 'How you obtained this (ex. gift or purchase).';
comment on column Video.comments is 'Any comments I have on this video.';
comment on column Video.lastModified is 'The time/date when this record was last modified.';
comment on column Video.region is 'What the region of the video is.  This can include standard like NTSC as well.';
comment on column Video.slipcoverYn is 'If the video has a slipcover or not.';
comment on column Video.oopYn is 'If the video is OOP or not.';
comment on column Video.discCount is 'The amount of disc(s).';
comment on column Video.caseType is 'The type of case for the video.';
comment on column Video.spineNumber is 'The spine number if used. Should be unique per publisher and format.';
comment on column Video.location is 'The location of the video.';
comment on column Video.totalMinutes is 'The total minutes of this video.';

    
alter table Video add CONSTRAINT PK_VIDEO PRIMARY KEY (videoId);
alter table Video add check (slipcoverYn in (0,1));
alter table Video add check (oopYn in (0,1));
create sequence SEQ_VIDEO START WITH 1 INCREMENT BY 1 NOCYCLE;

create table VideoAudit 
    (videoId number(8) NOT NULL,
     title varchar2(100) NOT NULL,
     videoType varchar2(15) NOT NULL,
     yearOfRelease number(4),
     rating varchar2(6),
     label varchar2(40),
     cost number(5,2),
     obtainedFrom varchar2(30),  
     obtainedType varchar2(30),
     region varchar2(10)
     oopYn number(1),  
     slipcoverYn number(1),  
     caseType varchar2(20),  
     discCount number(2), 
     spineNumber varchar2(7), 
     location varchar2(30),	 
     totalMinutes number(4), 
     comments varchar2(4000),
     lastModified DATE,
     auditDate DATE NOT NULL,
     auditAction varchar2(2) NOT NULL,
     userId varchar2(50) NOT NULL);

comment on table VideoAudit is 'Shawn McKenna''s Video Collection Audit table.';
comment on column VideoAudit.videoId is 'The primary key for the video table.';
comment on column VideoAudit.title is 'The title of the video.';
comment on column VideoAudit.videoType is 'The type of media for the video.  A DVD, BD or VHS etc...';
comment on column VideoAudit.yearOfRelease is 'The year the video is released.  It is the date printed of the video.';
comment on column VideoAudit.rating is 'The rating I gave the video.  *, **, **1/2, **** etc.  I use the four-star system.';
comment on column VideoAudit.label is 'The label that produced this video (on the DVD, VHS, etc...)';
comment on column VideoAudit.cost is 'The cost of the video (purchase price.)';
comment on column VideoAudit.comments is 'Any comments I have on this video.';
comment on column VideoAudit.obtainedFrom is 'The retailer, person or place you obtained (bought, gift) this from.';
comment on column VideoAudit.obtainedType is 'How you obtained this (ex. gift or purchase).';
comment on column VideoAudit.lastModified is 'The time/date when this record was last modified.';
comment on column VideoAudit.auditDate is 'The date/time when this auditing took place.';
comment on column VideoAudit.auditAction is 'The action of this audit: such as I for insert, D for delete, etc...';
comment on column VideoAudit.userId is 'The user that performed this audit.';
comment on column VideoAudit.region is 'What the region of the video is.  This can include standard like NTSC as well.';
comment on column VideoAudit.slipcoverYn is 'If the video has a slipcover or not.';
comment on column VideoAudit.oopYn is 'If the video is OOP or not.';
comment on column VideoAudit.discCount is 'The amount of disc(s).';
comment on column VideoAudit.caseType is 'The type of case for the video.';
comment on column VideoAudit.spineNumber is 'The spine number if used. Should be unique per publisher and format.';
comment on column VideoAudit.location is 'The location of the video.';
comment on column VideoAudit.totalMinutes is 'The total minutes of this video.';

create table Movie 
    (movieId number(9) NOT NULL,
     title varchar2(100) NOT NULL,
     year number(4),
     runtime number(4),
     country varchar2(30),
     language varchar2(30),
     mpaaRating varchar2(10),
     myRating varchar2(6),
     lastModified DATE,
     lastWatched DATE);

comment on table Movie is 'The Movie and its related information.';
comment on column Movie.movieId is 'The primary key to the movie table.';
comment on column Movie.videoId is 'The foreign key to the video table.';
comment on column Movie.title is 'The title of the Movie.';
comment on column Movie.year is 'The year the movie is released.';
comment on column Movie.runtime is 'The runtime of the movie in minutes.';
comment on column Movie.mpaaRating is 'This is the MPAA rating the movie obtained.  For example PG, R, NC-17.';
comment on column Movie.myRating is 'The rating I gave the video.  *, **, **1/2, **** etc.  I use the four-star system.';
comment on column Movie.lastModified is 'The time/date when this record was last modified.';
comment on column Movie.country is 'The main country or countries for the movie.  This can be defined by production company origin 
or other criteria.';
comment on column Movie.language is 'The main languge of the film.';
comment on column Movie.lastWatched is 'The last time the film was watched.';

ALTER TABLE MOVIE ADD CONSTRAINT PK_MOVIE PRIMARY KEY (movieId);
create sequence SEQ_MOVIE START WITH 1 INCREMENT BY 1 NOCYCLE;
    
create table MovieAudit 
    (movieId number(9) NOT NULL,
     title varchar2(100) NOT NULL,
     year number(4),
     runtime number(4),
     country varchar2(30),
     language varchar2(30),
     mpaaRating varchar2(10),
     myRating varchar2(6),
     lastModified DATE,
     auditDate DATE NOT NULL,
     auditAction varchar2(2) NOT NULL,
     userId varchar2(50) NOT NULL);

comment on table MovieAudit is 'The audit table for Movie and its related information.';

comment on column MovieAudit.movieId is 'The primary key to the movie table.';
comment on column MovieAudit.videoId is 'The foreign key to the video table.';
comment on column MovieAudit.title is 'The title of the Movie.';
comment on column MovieAudit.year is 'The year the movie is released.';
comment on column MovieAudit.runtime is 'The runtime of the movie in minutes.';
comment on column MovieAudit.mpaaRating is 'This is the MPAA rating the movie obtained.  For example PG, R, NC-17.';
comment on column MovieAudit.myRating is 'The rating I gave the video.  *, **, **1/2, **** etc.  I use the four-star system.';
comment on column MovieAudit.lastModified is 'The time/date when this record was last modified.';
comment on column MovieAudit.auditDate is 'The date/time when this auditing took place.';
comment on column MovieAudit.auditAction is 'The action of this audit: such as I for insert, D for delete, etc...';
comment on column MovieAudit.userId is 'The user that performed this audit.';
comment on column MovieAudit.country is 'The main country or countries for the movie. This can be defined by production company 
origin or other criteria.';
comment on column MovieAudit.language is 'The main languge of the film.';
comment on column MovieAudit.lastWatched is 'The last time the film was watched.';

create table MovieWatchHistory 
    (movieWatchHistoryId number(9) NOT NULL, 
     movieId number(9) NOT NULL,
     watched DATE,
     watchType varchar2(10),
     lastModified DATE);

comment on table MovieWatchHistory is 'The Movie watch history.';
comment on column MovieWatchHistory.movieId is 'The primary key to the movie table -- what movie was watched.';
comment on column MovieWatchHistory.watched is 'When the movie was watched.';
comment on column MovieWatchHistory.watchType is 'The Type of watch: theater, broadcast, DVD, blu-ray ...';
comment on column MovieWatchHistory.lastModified is 'The time/date when this record was last modified.';

ALTER TABLE MovieWatchHistory ADD CONSTRAINT PK_MOVIEWATCHHISTORY PRIMARY KEY (movieWatchHistoryId);
create sequence SEQ_MOVIEWATCHHISTORY START WITH 1 INCREMENT BY 1 NOCYCLE;

create table VideoMovie  
    (videoMovieId number(11) NOT NULL, 
     videoId number(8) NOT NULL,
     movieId number(9) NOT NULL,
     lastModified DATE);

comment on table VideoMovie is 'The table for the associative relationship between video and movie.';
comment on column VideoMovie.videoMovieId is 'The primary key to this associative table.';
comment on column VideoMovie.videoId is 'The foreign key to the video table.';
comment on column VideoMovie.movieId is 'The foreign key to the movie table.';
comment on column VideoMovie.lastModified is 'The time/date when this record was last modified.';

ALTER TABLE VideoMovie ADD CONSTRAINT PK_VIDEOMOVIE PRIMARY KEY (videoMovieId);
ALTER TABLE VideoMovie ADD CONSTRAINT FK_VMVideo FOREIGN KEY (videoId) REFERENCES Video (videoId) ON DELETE CASCADE;
ALTER TABLE VideoMovie ADD CONSTRAINT FK_VMMovie FOREIGN KEY (movieId) REFERENCES Movie (movieId) ON DELETE CASCADE;
create sequence SEQ_VIDEOMOVIE START WITH 1 INCREMENT BY 1 NOCYCLE;

create table VideoMovieAudit  
    (videoMovieId number(11) NOT NULL,
     videoId number(8) NOT NULL,
     movieId number(9) NOT NULL,
     lastModified DATE,
     auditDate DATE NOT NULL,
     auditAction varchar2(2) NOT NULL,
     userId varchar2(50) NOT NULL);

comment on table VideoMovieAudit is 'The audit table for the associative relationship between video and movie.';
comment on column VideoMovieAudit.videoMovieId is 'The primary key to this associative table.';
comment on column VideoMovieAudit.videoId is 'The foreign key to the video table.';
comment on column VideoMovieAudit.movieId is 'The foreign key to the movie table.';
comment on column VideoMovieAudit.lastModified is 'The time/date when this record was last modified.';
comment on column VideoMovieAudit.auditDate is 'The date/time when this auditing took place.';
comment on column VideoMovieAudit.auditAction is 'The action of this audit: such as I for insert, D for delete, etc...';
comment on column VideoMovieAudit.userId is 'The user that performed this audit.';

create table Trailer 
    (trailerId number(11) NOT NULL,
     movieId number(9) NOT NULL,
     embedCode varchar2(130),
     description varchar2(100),
     lastModified DATE);

comment on table Trailer is 'The table for movie trailers.';
comment on column Trailer.trailerId is 'The primary key to this table.';
comment on column Trailer.movieId is 'The foreign key to the movie table.';
comment on column Trailer.embedCode is 'The embedded code to access the trailer.';
comment on column Trailer.embedCode is 'A description of the trailer.';
comment on column Trailer.lastModified is 'The time/date when this record was last modified.';

ALTER TABLE Trailer ADD CONSTRAINT PK_TRAILER PRIMARY KEY (trailerId);
ALTER TABLE Trailer ADD CONSTRAINT FK_TrailerMovie FOREIGN KEY (movieId) REFERENCES Movie (movieId) ON DELETE CASCADE;

create table VideoCommentary  
     (videoCommentaryId number(11) NOT NULL, 
     videoMovieId number(11) NOT NULL, 
     commentators varchar2(100) NOT NULL, 
     lastModified DATE);

comment on table VideoCommentary is 'The table for the video commentary records.';
comment on column VideoCommentary.videoCommentaryId is 'The primary key for the Video Commentary.';
comment on column VideoCommentary.videoMovieId is 'The foreign key to the video movie table.';
comment on column VideoCommentary.commentators is 'The commentators on the video movie.';
comment on column VideoCommentary.lastModified is 'The time/date when this record was last modified.';

ALTER TABLE VideoCommentary ADD CONSTRAINT PK_VIDEOCOMMENTARY PRIMARY KEY (videoCommentaryId);
ALTER TABLE VideoCommentary ADD CONSTRAINT FK_VCVIDEOMOVIE FOREIGN KEY (videoMovieId) REFERENCES VideoMovie (videoMovieId) ON 
DELETE CASCADE;
create sequence SEQ_VIDEOCOMMENTARY START WITH 1 INCREMENT BY 1 NOCYCLE;

create table VideoCommentaryAudit  
    (videoCommentaryId number(11) NOT NULL,
     videoMovieId number(8) NOT NULL,
     commentators varchar2(100) NOT NULL, 
     lastModified DATE,
     auditDate DATE NOT NULL,
     auditAction varchar2(2) NOT NULL,
     userId varchar2(50) NOT NULL);

comment on table VideoCommentaryAudit is 'The audit table for the video commentary records.';
comment on column VideoCommentaryAudit.videoCommentaryId is 'The primary key for the Video Commentary.';
comment on column VideoCommentaryAudit.videoMovieId is 'The foreign key to the video movie table.';
comment on column VideoCommentaryAudit.commentators is 'The commentators on the video movie.';
comment on column VideoCommentaryAudit.lastModified is 'The time/date when this record was last modified.';
comment on column VideoCommentaryAudit.auditDate is 'The date/time when this auditing took place.';
comment on column VideoCommentaryAudit.auditAction is 'The action of this audit: such as I for insert, D for delete, etc...';
comment on column VideoCommentaryAudit.userId is 'The user that performed this audit.';

create table VideoExtra  
     (videoExtraId number(11) NOT NULL, 
     videoId number(11) NOT NULL, 
     title varchar2(60) NOT NULL, 
     description varchar2(150), 
     lastModified DATE);

comment on table VideoExtra is 'The table for the video extra(s).';
comment on column VideoExtra.videoExtraId is 'The primary key for the Video Extra table.';
comment on column VideoExtra.videoId is 'The foreign key to the video table.';
comment on column VideoExtra.title is 'The title of the video extra.';
comment on column VideoExtra.description is 'The description of the video extra.';
comment on column VideoExtra.lastModified is 'The time/date when this record was last modified.';

ALTER TABLE VideoExtra ADD CONSTRAINT PK_VIDEOEXTRA PRIMARY KEY (videoExtraId);
ALTER TABLE VideoExtra ADD CONSTRAINT FK_VEVIDEO FOREIGN KEY (videoId) REFERENCES Video (videoId) ON DELETE CASCADE;
create sequence SEQ_VIDEOEXTRA START WITH 1 INCREMENT BY 1 NOCYCLE;

create table VideoExtraAudit  
     (videoExtraId number(11) NOT NULL, 
     videoId number(11) NOT NULL, 
     title varchar2(60) NOT NULL, 
     description varchar2(150), 
     lastModified DATE,
     auditDate DATE NOT NULL,
     auditAction varchar2(2) NOT NULL,
     userId varchar2(50) NOT NULL);

comment on table VideoExtraAudit is 'The audit table for the video extra(s).';
comment on column VideoExtraAudit.videoExtraId is 'The primary key for the Video table.';
comment on column VideoExtraAudit.videoId is 'The foreign key to the video table.';
comment on column VideoExtraAudit.title is 'The title of the video extra.';
comment on column VideoExtraAudit.description is 'The description of the video extra.';
comment on column VideoExtraAudit.lastModified is 'The time/date when this record was last modified.';
comment on column VideoExtraAudit.auditDate is 'The date/time when this auditing took place.';
comment on column VideoExtraAudit.auditAction is 'The action of this audit: such as I for insert, D for delete, etc...';
comment on column VideoExtraAudit.userId is 'The user that performed this audit.';

create table MovieReview 
    (movieReviewId number(11) NOT NULL,
     movieId number(9) NOT NULL,
     review CLOB,
     lastModified DATE);

create sequence SEQ_MOVIEREVIEW START WITH 1 INCREMENT BY 1 NOCYCLE;
comment on table MovieReview is 'The Movie Review table.';
comment on column MovieReview.movieId is 'The foreign key to the movie table.';
comment on column MovieReview.review is 'The review.';
comment on column MovieReview.lastModified is 'The time/date when this record was last modified.';

create table VideoMisc  
     (videoMiscId number(11) NOT NULL, 
     videoMovieId number(11) NOT NULL, 
     videoMisc varchar2(100) NOT NULL,
     warningYn number(1), 
     lastModified DATE);

comment on table VideoMisc is 'The table for the video miscellaneous data.';
comment on column VideoMisc.videoMiscId is 'The primary key for the Video Misc.';
comment on column VideoMisc.videoMovieId is 'The foreign key to the video movie table.';
comment on column VideoMisc.videoMisc is 'The video Misc description.';
comment on column VideoMisc.warningYn is 'If the misc is a warning or fault.';
comment on column VideoMisc.lastModified is 'The time/date when this record was last modified.';

ALTER TABLE VideoMisc ADD CONSTRAINT PK_VIDEOMISC PRIMARY KEY (videoMiscId);
ALTER TABLE VideoMisc ADD CONSTRAINT FK_VMVIDEOMOVIE FOREIGN KEY (videoMovieId) REFERENCES VideoMovie (videoMovieId) ON DELETE 
CASCADE;
ALTER TABLE VideoMisc ADD CHECK (warningYn in (0,1));
create sequence SEQ_VIDEOMISC START WITH 1 INCREMENT BY 1 NOCYCLE;

create table VideoMiscAudit  
     (videoMiscId number(11) NOT NULL, 
     videoMovieId number(11) NOT NULL, 
     videoMisc varchar2(100) NOT NULL, 
     warningYn number(1), 
     lastModified DATE,
     auditDate DATE NOT NULL,
     auditAction varchar2(2) NOT NULL,
     userId varchar2(50) NOT NULL);

comment on table VideoMiscAudit is 'The table for the video miscellaneous data.';
comment on column VideoMiscAudit.videoMiscId is 'The primary key for the Video Misc.';
comment on column VideoMiscAudit.videoMovieId is 'The foreign key to the video movie table.';
comment on column VideoMiscAudit.videoMisc is 'The video Misc description.';
comment on column VideoMiscAudit.warningYn is 'If the misc is a warning or fault.';
comment on column VideoMiscAudit.lastModified is 'The time/date when this record was last modified.';
comment on column VideoMiscAudit.auditDate is 'The date/time when this auditing took place.';
comment on column VideoMiscAudit.auditAction is 'The action of this audit: such as I for insert, D for delete, etc...';
comment on column VideoMiscAudit.userId is 'The user that performed this audit.';

create table LendingHistory  
     (lendingHistoryId NUMBER(11) NOT NULL, 
     videoId NUMBER(11) NOT NULL, 
     lentTo VARCHAR2(60) NOT NULL, 
     lendingDate DATE,
     returnDate DATE,
     returnedYn NUMBER(1),
     didNotWatchYn NUMBER(1),    
     lastModified DATE);

comment on table LendingHistory is 'The table for the lending history.';
comment on column LendingHistory.lendingHistoryId is 'The primary key for the Lending History.';
comment on column LendingHistory.videoId is 'The foreign key to the video table.';
comment on column LendingHistory.lentTo is 'The person/subject you lent the video to.';
comment on column LendingHistory.lendingDate is 'The lending date.';
comment on column LendingHistory.returnDate is 'The return date.';
comment on column LendingHistory.returnedYn is 'Whether it was returned or not.  You can use this if you do not want to use or have 
the dates.';
comment on column LendingHistory.didNotWatchYn is "Did not watch the video.";
comment on column LendingHistory.lastModified is 'The time/date when this record was last modified.';

ALTER TABLE LendingHistory ADD CONSTRAINT PK_LENDINGHISTORY PRIMARY KEY (lendingHistoryId);
ALTER TABLE LendingHistory ADD CONSTRAINT FK_LHVIDEO FOREIGN KEY (videoId) REFERENCES Video (videoId) ON DELETE CASCADE;
create sequence SEQ_LENDINGHISTORY START WITH 1 INCREMENT BY 1 NOCYCLE;
alter table LendingHistory ADD CHECK (returnedYn in (0,1));

create table Person   
     (personId number(11) NOT NULL, 
     person varchar2(100) NOT NULL, 
     birthdate DATE,
     frontPicEmbed varchar2(130),
     lastModified DATE);
comment on table Person is 'The table for the particular subject.';
comment on column Person.personId is 'The primary key for the Subject.';
comment on column Person.person is 'The person,might want to split into fname, lname later.';
comment on column Person.birthdate  is 'The birthdate for the Subject.';
comment on column Person.frontPicEmbed is 'The front picture embed code.';
comment on column Person.lastModified is 'The time/date when this record was last modified.';
create sequence SEQ_PERSON START WITH 1 INCREMENT BY 1 NOCYCLE;

-- alters 
alter table Video add obtainedFrom varchar2(30);
alter table VideoAudit add obtainedFrom varchar2(30);
alter table Video add obtainedType varchar2(30);
alter table VideoAudit add obtainedType varchar2(30);
alter table Movie drop column videoId;
alter table MovieAudit drop column videoId;
alter table Video add slipcoverYn number(1);
alter table VideoAudit add slipcoverYn varchar2(1);
alter table Video add oopYn number(1);
alter table VideoAudit add oopYn varchar2(1);
alter table Video add region varchar2(10);
alter table VideoAudit add region varchar2(10);
alter table Video add discCount number(2);
alter table VideoAudit add discCount varchar2(2);
alter table Movie add country varchar2(30);
alter table MovieAudit add country varchar2(30);
alter table Movie add language varchar2(30);
alter table MovieAudit add language varchar2(30);
alter table Movie add lastWatched date;
alter table MovieAudit add lastWatched date;
alter table TRAILER MODIFY EMBEDCODE VARCHAR2(130);
alter table VideoMisc add warningYn number(1);
alter table VideoMiscAudit add warningYn number(1);
alter table Video add caseType varchar2(20);
alter table VideoAudit add caseType varchar2(20);
alter table LendingHistory ADD didNotWatchYn NUMBER(1);
alter table Video add spineNumber varchar2(5);
alter table VideoAudit add spineNumber varchar2(5);
alter table Video add location varchar2(30);
alter table VideoAudit add location varchar2(30);
alter table Video modify spineNumber varchar2(7);
alter table VideoAudit modify spineNumber varchar2(7);
comment on column LendingHistory.didNotWatchYn is 'Did not watch the video.';
comment on column Video.spineNumber is 'The spine number if used. Should be unique per publisher and format.';
comment on column VideoAudit.spineNumber is 'The spine number if used. Should be unique per publisher and format.';
comment on column Video.location is 'The location of the video.';
comment on column VideoAudit.location is 'The location of the video.';

-- drops
drop sequence SEQ_VIDEO;

UPDATE VIDEOEXTRA SET DESCRIPTION = RTRIM(LTRIM(DESCRIPTION,'('), ')');

create table Book 
    (bookId number(8) PRIMARY KEY,
     title varchar2(100) NOT NULL,
     yearFirstPublished number(4), 
     yearPublished number(4),
     pages number(4),
     myRating varchar2(6),
     publisher varchar2(40),
     notes varchar2(4000),
     lastModified DATE);

alter table Book add type varchar2(20);

comment on table Book is 'Shawn McKenna''s Book Collection';

comment on column Book.bookId is 'The primary key for the book table.';
comment on column Book.title is 'The title of the book.';
comment on column Book.yearFirstPublished is 'The year the book was first published.';
comment on column Book.yearPublished is 'The year the book is published.';
comment on column Book.pages is 'The amount of pages.';
comment on column Book.myRating is 'The rating I gave the book.  *, **, **1/2, **** etc.  I use the four-star system.';
comment on column Book.publisher is 'The book publisher.';
comment on column Book.notes is 'Any notes I have on this book.';
comment on column Book.type is 'What type like softcover or hardback.';
comment on column Book.lastModified is 'The time/date when this file was last modified.';

create table Studio 
    (studioId number(8) PRIMARY KEY,
     studio varchar2(100) NOT NULL,
     lastModified DATE
);

comment on table Studio is 'The Studio.';

comment on column Studio.studioId is 'The primary key for studio.';
comment on column Studio.studio is 'The name of the studio.';
comment on column Studio.lastModified is 'The time/date when this file was last modified.';


create table MovieStudio 
    (movieStudioId number(11),
     movieId number(8) NOT NULL,
     studioId number(8) NOT NULL,
     lastModified DATE
);

ALTER TABLE MovieStudio ADD CONSTRAINT PK_MOVIESTUDIO PRIMARY KEY (movieStudioId);
ALTER TABLE MovieStudio ADD CONSTRAINT FK_MSMovie FOREIGN KEY (movieId) REFERENCES Movie (movieId) ON DELETE CASCADE;
ALTER TABLE MovieStudio ADD CONSTRAINT FK_MSStudio FOREIGN KEY (studioId) REFERENCES Studio (studioId) ON DELETE CASCADE;

comment on table MovieStudio is 'The table for the associative relationship between movie and studio.';
comment on column MovieStudio.movieStudioId is 'The primary key to this associative table.';
comment on column MovieStudio.movieId is 'The foreign key to the movie table.';
comment on column MovieStudio.studioId is 'The foreign key to the studio table.';
comment on column MovieStudio.lastModified is 'The time/date when this record was last modified.';

create table Genre 
    (genreId number(8) PRIMARY KEY,
     genre varchar2(100) NOT NULL,
     lastModified DATE
);
comment on table Genre is 'The Genre.';

comment on column Genre.genreId is 'The primary key for genre.';
comment on column Genre.genre is 'The genre of the movie.';
comment on column Genre.lastModified is 'The time/date when this file was last modified.';

create table MovieGenre 
    (movieGenreId number(11),
     movieId number(8) NOT NULL,
     genreId number(8) NOT NULL,
     lastModified DATE
);

ALTER TABLE MovieGenre ADD CONSTRAINT PK_MOVIEGENRE PRIMARY KEY (movieGenreId);
ALTER TABLE MovieGenre ADD CONSTRAINT FK_MGMovie FOREIGN KEY (movieId) REFERENCES Movie (movieId) ON DELETE CASCADE;
ALTER TABLE MovieGenre ADD CONSTRAINT FK_MGGenre FOREIGN KEY (genreId) REFERENCES Genre (genreId) ON DELETE CASCADE;

comment on table MovieGenre is 'The table for the associative relationship between movie and genre.';
comment on column MovieGenre.movieGenreId is 'The primary key to this associative table.';
comment on column MovieGenre.movieId is 'The foreign key to the movie table.';
comment on column MovieGenre.genreId is 'The foreign key to the genre table.';
comment on column MovieGenre.lastModified is 'The time/date when this record was last modified.';

create table Series 
    (seriesId number(9) NOT NULL,
     title varchar2(100) NOT NULL,
     seasons number(2), 
     startingYear number(4),
     endingYear number (4), 
     country varchar2(30),
     language varchar2(30),
     myRating varchar2(6),
     lastModified DATE);

comment on table Series is 'The Series and its related information.';
comment on column Series.seriesId is 'The primary key to the series table.';
comment on column Series.title is 'The title of the Series.';
comment on column Series.startingYear is 'The starting year of the TV series.';
comment on column Series.endingYear is 'The ending year of the TV series.';
comment on column Series.country is 'The main country or countries for the series.  This can be defined by production company 
origin or other criteria.';
comment on column Series.language is 'The main languge of the series.';
comment on column Series.myRating is 'The rating I gave the series.  *, **, **1/2, **** etc.  I use the four-star system.';
comment on column Series.lastModified is 'The time/date when this record was last modified.';

ALTER TABLE Series ADD CONSTRAINT PK_Series PRIMARY KEY (seriesId);
CREATE SEQUENCE SEQ_Series START WITH 1 INCREMENT BY 1 NOCYCLE;

create table SeriesAudit 
    (seriesId number(9) NOT NULL,
     title varchar2(100) NOT NULL,
     seasons number(2), 
     startingYear number(4),
     endingYear number (4), 
     country varchar2(30),
     language varchar2(30),
     myRating varchar2(6),
     lastModified DATE,
     auditDate DATE NOT NULL,
     auditAction varchar2(2) NOT NULL,
     userId varchar2(50) NOT NULL);

comment on table SeriesAudit is 'The audit table for Series and its related information.';
comment on column SeriesAudit.seriesId is 'The primary key to the series table.';
comment on column SeriesAudit.title is 'The title of the Series.';
comment on column SeriesAudit.startingYear is 'The starting year of the TV series.';
comment on column SeriesAudit.endingYear is 'The ending year of the TV series.';
comment on column SeriesAudit.country is 'The main country or countries for the series.  This can be defined by production company 
origin or other criteria.';
comment on column SeriesAudit.language is 'The main languge of the series.';
comment on column SeriesAudit.myRating is 'The rating I gave the series.  *, **, **1/2, **** etc.  I use the four-star system.';
comment on column SeriesAudit.lastModified is 'The time/date when this record was last modified.';
comment on column SeriesAudit.auditDate is 'The date/time when this auditing took place.';
comment on column SeriesAudit.auditAction is 'The action of this audit: such as I for insert, D for delete, etc...';
comment on column SeriesAudit.userId is 'The user that performed this audit.';

create table VideoSeries  
    (videoSeriesId number(11) NOT NULL, 
     videoId number(8) NOT NULL,
     seriesId number(9) NOT NULL,
     lastModified DATE);

comment on table VideoSeries is 'The table for the associative relationship between video and series.';
comment on column VideoSeries.videoSeriesId is 'The primary key to this associative table.';
comment on column VideoSeries.videoId is 'The foreign key to the video table.';
comment on column VideoSeries.seriesId is 'The foreign key to the series table.';
comment on column VideoSeries.lastModified is 'The time/date when this record was last modified.';

ALTER TABLE VideoSeries ADD CONSTRAINT PK_VideoSeries PRIMARY KEY (videoSeriesId);
ALTER TABLE VideoSeries ADD CONSTRAINT FK_VSVideo FOREIGN KEY (videoId) REFERENCES Video (videoId) ON DELETE CASCADE;
ALTER TABLE VideoSeries ADD CONSTRAINT FK_VSSeries FOREIGN KEY (seriesId) REFERENCES Series (seriesId) ON DELETE CASCADE;
create sequence SEQ_VideoSeries START WITH 1 INCREMENT BY 1 NOCYCLE;

create table VideoSeriesAudit  
    (videoSeriesId number(11) NOT NULL, 
     videoId number(8) NOT NULL,
     seriesId number(9) NOT NULL,
     lastModified DATE,
     auditDate DATE NOT NULL,
     auditAction varchar2(2) NOT NULL,
     userId varchar2(50) NOT NULL);
    
comment on table VideoSeriesAudit is 'The audit table for the associative relationship between video and series.';
comment on column VideoSeriesAudit.videoSeriesId is 'The primary key to this associative table.';
comment on column VideoSeriesAudit.videoId is 'The foreign key to the video table.';
comment on column VideoSeriesAudit.seriesId is 'The foreign key to the series table.';
comment on column VideoSeriesAudit.lastModified is 'The time/date when this record was last modified.';
comment on column VideoSeriesAudit.auditDate is 'The date/time when this auditing took place.';
comment on column VideoSeriesAudit.auditAction is 'The action of this audit: such as I for insert, D for delete, etc...';
comment on column VideoSeriesAudit.userId is 'The user that performed this audit.';

create table Collection 
    (collectionId number(8) PRIMARY KEY,
     collectionName varchar2(100) NOT NULL,
     lastModified DATE
);
comment on table Collection is 'The Collection.';

comment on column Collection.collectionId is 'The primary key for collection.';
comment on column Collection.collectionName is 'The collection name.';
comment on column Collection.lastModified is 'The time/date when this record was last modified.';

create table VideoCollection 
    (videoCollectionId number(11),
     videoId number(8) NOT NULL,
     collectionId number(8) NOT NULL,
     lastModified DATE
);

ALTER TABLE VideoCollection ADD CONSTRAINT PK_VIDEOCOLLECTION PRIMARY KEY (videoCollectionId);
ALTER TABLE VideoCollection ADD CONSTRAINT FK_VCVideo FOREIGN KEY (videoId) REFERENCES Video (videoId) ON DELETE CASCADE;
ALTER TABLE VideoCollection ADD CONSTRAINT FK_VCCollection FOREIGN KEY (collectionId) REFERENCES Collection (collectionId) ON 
DELETE CASCADE;

comment on table VideoCollection is 'The table for the associative relationship between video and collection.';
comment on column VideoCollection.videoCollectionId is 'The primary key to this associative table.';
comment on column VideoCollection.videoId is 'The foreign key to the video table.';
comment on column VideoCollection.collectionId is 'The foreign key to the collection table.';
comment on column VideoCollection.lastModified is 'The time/date when this record was last modified.';

CREATE TABLE VideoBoxSet 
    (videoBoxSetId number(11),
     boxSetId number(8) NOT NULL,
     videoId number(8) NOT NULL,
     lastModified DATE
);
ALTER TABLE VideoBoxSet ADD CONSTRAINT PK_VIDEOSET PRIMARY KEY (videoBoxSetId);
ALTER TABLE VideoBoxSet ADD CONSTRAINT FK_VSBoxSet FOREIGN KEY (boxSetId) REFERENCES Video (videoId) ON DELETE CASCADE;
ALTER TABLE VideoBoxSet ADD CONSTRAINT FK_VBSVideo FOREIGN KEY (videoId) REFERENCES Video (videoId) ON DELETE CASCADE;

comment on table VideoBoxSet is 'The table for keeping track of video box sets.';
comment on column VideoBoxSet.videoBoxSetId is 'The primary key to this associative table.';
comment on column VideoBoxSet.boxSetId is 'The foreign key to the video table for box sets.';
comment on column VideoBoxSet.videoId is 'The foreign key to the video table for videos associated with box sets.';
comment on column VideoBoxSet.lastModified is 'The time/date when this record was last modified.';
