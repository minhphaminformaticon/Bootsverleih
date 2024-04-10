DROP TABLE IF EXISTS boat;

CREATE TABLE boat(
    id INT AUTO_INCREMENT PRIMARY KEY,
    model VARCHAR(255) NOT NULL,
    vehiclelicenseplate VARCHAR(8) UNIQUE
);

INSERT INTO boat (vehiclelicenseplate)
VALUES
('ABC12345'),
('DEF67890'),
('GHI54321');
