CREATE TABLE IF NOT EXISTS svc_wedoogift.gift_deposit
(
    -- Default part.
    id              uuid        NOT NULL DEFAULT public.uuid_generate_v4(),
    created_at      timestamptz NOT NULL DEFAULT NOW(),
    updated_at      timestamptz NOT NULL DEFAULT NOW(),
    -- Custom part.
    company_id      uuid        NOT NULL,
    user_id         uuid        NOT NULL,
    amount          numeric     NOT NULL,
    deposit_date    date        NOT NULL,
    expiration_date date        NOT NULL,
    -- Constraints.
    PRIMARY KEY (id),
    FOREIGN KEY (company_id) REFERENCES svc_wedoogift.company (id),
    FOREIGN KEY (user_id) REFERENCES svc_wedoogift.user (id)
);
