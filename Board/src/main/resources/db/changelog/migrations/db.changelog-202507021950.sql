--liquibase formattedd sql
--changeset leandro:2024081938
--comment: set unblock_reason nullable

ALTER TABLE board.blocks MODIFY COLUMN unblock_reason VARCHAR(255) NULL;


--rollback ALTER TABLE board.blocks MODIFY COLUMN unblock_reason VARCHAR(255) NOT NULL