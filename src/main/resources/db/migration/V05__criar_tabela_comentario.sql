CREATE TABLE comentario (
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	ordem_servico_id BIGINT NOT NULL,
	descricao TEXT NOT NULL,
	data_envio DATETIME NOT NULL
);

ALTER TABLE comentario ADD CONSTRAINT fk_comentario_ordem_servico
FOREIGN KEY (ordem_servico_id) REFERENCES ordem_servico(id);