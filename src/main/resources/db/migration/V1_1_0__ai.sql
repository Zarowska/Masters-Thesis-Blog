-- Create table for PersonDescriptionEntity
CREATE TABLE ai_person_description (
                                       id UUID PRIMARY KEY,
                                       full_name VARCHAR(255),
                                       age INT,
                                       gender VARCHAR(50),
                                       nationality VARCHAR(100),
                                       origin VARCHAR(100),
                                       live_city VARCHAR(100),
                                       live_country VARCHAR(100),
                                       email VARCHAR(255),
                                       bio TEXT,
                                       appearance TEXT,
                                       last_feed UUID default null
);

-- Create table for PersonInterestEntity
CREATE TABLE person_interests (
                                  id UUID PRIMARY KEY,
                                  title VARCHAR(255),
                                  description TEXT,
                                  person_description_entity_id UUID,
                                  FOREIGN KEY (person_description_entity_id) REFERENCES ai_person_description(id)
);