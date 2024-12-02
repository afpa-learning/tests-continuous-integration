
DROP TABLE if exists public.client CASCADE;
DROP TABLE if exists public.account CASCADE;

CREATE TABLE "client" (
	id uuid PRIMARY KEY,
	first_name VARCHAR(100),
	last_name VARCHAR(100),
	birthdate date,
	email VARCHAR(50)
);

CREATE TABLE account (
	id SERIAL PRIMARY KEY,
	creation_time timestamp,
	balance bigint,
	active boolean,
	client_id uuid,
	CONSTRAINT fk_client FOREIGN KEY(client_id) REFERENCES "client"(id)
);

