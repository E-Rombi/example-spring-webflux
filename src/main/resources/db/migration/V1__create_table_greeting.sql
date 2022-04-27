CREATE TABLE IF NOT EXISTS public.greeting
(
    id SERIAL PRIMARY KEY,
    name text COLLATE pg_catalog."default" NOT NULL
)