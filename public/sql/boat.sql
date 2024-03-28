DROP TABLE IF EXISTS boat;

CREATE TABLE boat(
    id INT AUTO_INCREMENT PRIMARY KEY,
    vehiclelicenseplate VARCHAR(8) UNIQUE
);

INSERT INTO boat (vehiclelicenseplate)
VALUES
('ABC12345'),
('DEF67890'),
('GHI54321');
