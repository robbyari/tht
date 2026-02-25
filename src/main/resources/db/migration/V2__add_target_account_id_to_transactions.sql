ALTER TABLE transactions
ADD COLUMN target_account_id BIGINT;

ALTER TABLE transactions
ADD CONSTRAINT fk_transactions_target_account
FOREIGN KEY (target_account_id) REFERENCES accounts(id);
