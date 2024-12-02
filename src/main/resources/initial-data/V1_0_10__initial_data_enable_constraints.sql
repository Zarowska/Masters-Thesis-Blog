ALTER TABLE users
    ADD CONSTRAINT users_profile_image_fk FOREIGN KEY (profile_image_id) REFERENCES images (id) ON DELETE SET NULL;
