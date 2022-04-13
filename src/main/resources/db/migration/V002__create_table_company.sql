CREATE TABLE IF NOT EXISTS svc_wedoogift.company
(
    -- Default part.
    id         uuid        NOT NULL DEFAULT public.uuid_generate_v4(),
    created_at timestamptz NOT NULL DEFAULT NOW(),
    updated_at timestamptz NOT NULL DEFAULT NOW(),
    -- Custom part.
    name       text        NOT NULL,
    gift_cash  numeric     NOT NULL DEFAULT 0,
    meal_cash  numeric     NOT NULL DEFAULT 0,
    -- Constraints.
    PRIMARY KEY (id)
);
