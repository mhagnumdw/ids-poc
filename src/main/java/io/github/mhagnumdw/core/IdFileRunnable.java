package io.github.mhagnumdw.core;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Gera os IDs em um arquivo. Por questões de otimização/velocidade na geração dos IDs,
 * primeiro eles são gerados em memória e só depois que são escritos em um arquivo.
 * <p/>
 * É necessário fazer uma contabilização anterior para saber se foram
 * ou não gerados IDs duplicados. Algo como:
 * <pre>
 * sort ~/ids-*.txt | uniq -d -c
 * sort -T ~/tmp-sort ~/ids-*.txt | uniq -d -c
 * LC_ALL=C sort --parallel=$(nproc) -S 50% -T /dev/shm ~/ids-*.txt | uniq -d -c
 * </pre>
 */
public abstract class IdFileRunnable implements Runnable {

    private final int totalIds;
    private final int fileIndex;

    /**
     * Construtor.
     * @param totalIds total de IDs que devem ser gerados
     * @param fileIndex um número único para ser adicionado ao nome do arquivo
     */
    protected IdFileRunnable(int totalIds, int fileIndex) {
        this.totalIds = totalIds;
        this.fileIndex = fileIndex;
    }

    @Override
    public final void run() {
        generate();
    }

    private void generate() {
        try {
            Path path = getOutputFile();
            Log.info("Início: " + path);

            List<Object> ids = new ArrayList<>(totalIds);
            Log.info("Gerando...");
            for (int i = 0; i < totalIds; i++) {
                ids.add(generateId());
            }
            Log.info("... gerou. Agora escrevendo o arquivo no disco...");

            Iterable<CharSequence> lines = () -> ids.stream().map(id -> (CharSequence) String.valueOf(id)).iterator();
            Files.write(path, lines);

            Log.info("Fim");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Path getOutputFile() {
        return Path.of(System.getProperty("user.home") + "/" + getIdName() + "-" + fileIndex + ".txt");
    }

    /**
     * Algum identificador para o tipo de ID em questão, como "ulid", "tsid", uuidv4 etc. Fará parte do nome do arquivo.
     */
    protected abstract String getIdName();

    /**
     * Sobrescrever para definir o local de onde os IDs são gerados.
     * @return o ID gerado
     */
    protected abstract Object generateId();

}
