ALTER TABLE IF EXISTS users
    ADD CONSTRAINT users_profile_image_fk FOREIGN KEY (profile_image_id)  REFERENCES images ON DELETE SET null;