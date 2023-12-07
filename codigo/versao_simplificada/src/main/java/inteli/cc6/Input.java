package inteli.cc6;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Input {

    private final ArrayList<Tecnico> tecnicos;
    private final String[] setores;
    private final ArrayList<Servico> servicos;
    
    public class Tecnico{
        private final String id;
        private String sec;

        public Tecnico(String i,String s){
            id = i; sec = s;
        }

        public String getMatricula(){return id;}
        public String getSetor(){return sec;}
        public void setSetor(String set){sec = set;}
    }

    public class Servico{
        private final String ba;
        private final String setor_a;
        private final String data_ab;
        private final String latitude;
        private final String longitude;

        public Servico(String id, String set, String dat, String lat, String lon){
            ba = id; setor_a = set; data_ab = dat; latitude = lat; longitude = lon;
        }
        public String getId(){return ba;}
        public String abertura(){return data_ab;}
        public String getSetor(){return setor_a;}
        public String[] getCoordenada() {return new String[] {latitude,longitude};}
    }

    public Input(BufferedReader tec, BufferedReader set, BufferedReader serv) throws IOException{
        String linT = tec.readLine();
        linT = tec.readLine();
        ArrayList<Tecnico> arrT = new ArrayList<Tecnico>();
        while(linT != null){
            String[] open = linT.split(";");
            arrT.add(new Tecnico(open[2],open[3]));
            linT = tec.readLine();
        }
        tecnicos = arrT;
        linT = set.readLine();
        linT = set.readLine();
        String csvS = "";
        while(linT != null){
            String[] open = linT.split(",");
            csvS += open[1] + ",";
            linT = set.readLine();
        }
        setores = csvS.split(",");
        linT = serv.readLine();
        linT = serv.readLine();
        ArrayList<Servico> arrSer = new ArrayList<Servico>();
        while(linT!= null){
            String[] open = linT.split(",");
            arrSer.add(new Servico(open[0], open[5], open[6], open[14], open[15]));
            linT = serv.readLine();
        }
        servicos = arrSer;


    }
    
    public static void main(String[] args) throws IOException {
        String[] filesPath = new String[3];
        try (Scanner scan = new Scanner(System.in)) {
            System.out.print("Tabela de tecnicos: ");
            filesPath[0] = scan.nextLine();
            System.out.print("\nTabela de setores: ");
            filesPath[1] = scan.nextLine();
            System.out.print("\nTabela de servicos: ");
            filesPath[2] = scan.nextLine();
            FileReader fileRead1 = new FileReader(filesPath[0]);
            BufferedReader read1 = new BufferedReader(fileRead1);
            FileReader fileRead2 = new FileReader(filesPath[1]);
            BufferedReader read2 = new BufferedReader(fileRead2);
            FileReader fileRead3 = new FileReader(filesPath[2]);
            BufferedReader read3 = new BufferedReader(fileRead3);
            Input dados = new Input(read1, read2, read3);
            System.out.println("\nSetores: " + dados.setores.length);
            System.out.println("\nTecnicos: " + dados.tecnicos.size());
            System.out.println("\nServicos: " + dados.servicos.size());
        }

    }
}
