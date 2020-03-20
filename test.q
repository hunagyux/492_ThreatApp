-- CREATE TABLE savedRepos (
--     _id INTEGER PRIMARY KEY AUTOINCREMENT,
--     fullName TEXT NOT NULL,
--     description TEXT,
--     url TEXT NOT NULL,
--     stars INTEGER DEFAULT 0,
--     timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
-- );

-- INSERT INTO savedRepos (
--   fullName, description, url, stars
-- ) VALUES (
--   'tensorflow/tensorflow3',
--   'Deep nets 3',
--   'http://....3',
--   12347
-- );

-- SELECT * FROM savedRepos;
-- SELECT _id, fullName from savedRepos;
-- SELECT * FROM savedRepos
-- WHERE stars > 12346;
-- SELECT * FROM savedRepos
-- WHERE fullName = 'tensorflow/tensorflow';
-- SELECT * FROM savedRepos
-- WHERE fullName LIKE 'tensorflow/%';
-- SELECT * FROM savedRepos
-- ORDER BY timestamp DESC;

-- UPDATE savedRepos
-- SET stars = 54321
-- WHERE _id = 1;

-- UPDATE savedRepos
-- SET description = "Awesome neural nets"
-- WHERE fullName LIKE 'tensorflow/%';

DELETE FROM savedRepos
WHERE _id = 3;
