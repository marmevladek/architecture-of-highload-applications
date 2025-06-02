CREATE TABLE swipes (
                        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                        swiper_id UUID,
                        target_id UUID,
                        direction_1 VARCHAR(255),
                        direction_2 VARCHAR(255)
);