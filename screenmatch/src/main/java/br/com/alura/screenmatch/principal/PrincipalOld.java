package br.com.alura.screenmatch.principal;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

public class PrincipalOld {

	private Scanner leitura = new Scanner(System.in);
	private ConsumoApi consumoApi = new ConsumoApi();
	private ConverteDados conversor = new ConverteDados();

	private final String ENDERECO = "https://www.omdbapi.com/?t=";
	private final String API_KEY = "&apikey=6585022c";

	public void exibeMenu() {
		System.out.println("Digite o nome da série para busca:");
		var nomeSerie = leitura.nextLine();
		var json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);

		// converter o json em um objeto dados do tipo DadosSerie
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
		//System.out.println(dados);

		// converter o json em temporadas do tipo DadosTemporada
		List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i <= dados.totalTemporadas(); i++) {
			json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		//temporadas.forEach(System.out::println); // mesma coisa que temporadas.forEach(t -> System.out.println(t))

		// PERCORRER AS TEMPORADAS E EXIBIR OS TITULOS DOS CADA EPISÓDIO
//		for(int i = 0; i < dados.totalTemporadas(); i++) {
//			List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
//			for (int j = 0; j < episodiosTemporada.size(); j++) {
//				System.out.println(episodiosTemporada.get(j).titulo());
//			}
//		}

		// CODIGO MELHORADO COM LAMBDAS
		//temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
		
		List<DadosEpisodio> dadosEpisodios = temporadas.stream() //iterar as temporadas
				.flatMap(t -> t.episodios().stream()) //criar uma listagem em cima da primeira com todos os episódios de todas as temporadas
				.collect(Collectors.toList()); //coletar esses dados e jogar em dadosEpisodios
		
		System.out.println("\n Top 10 espisódios:");
		
		dadosEpisodios.stream()
			.filter(e -> !e.avaliacao().equalsIgnoreCase("N/A")) //filtrar e ignorar as avaliacoes N/A
			//.peek(e-> System.out.println("Primeiro filtro(N/A) " + e))
			.sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed()) //ordenar decrescente e comparar as avaliacoes 
			//.peek(e -> System.out.println("Ordenção " + e))
			.limit(10) //pegar as primeiras 5 linhas
			.map(e -> e.titulo().toUpperCase())
			.forEach(System.out::println);	
		
		//criar um objeto do tipo episodio onde terá o número da temporada
		System.out.println("\n Episódios:");
		List<Episodio> episodios = temporadas.stream() //iterar as temporadas
				.flatMap(t -> t.episodios().stream() //criar uma listagem em cima da primeira com todos os episódios de todas as temporadas
						.map(d -> new Episodio(t.numero(), d)) //criar um objeto do tipo episodio em cada iteracao de episodios passando os dados deles e acrescentando a temporada
		        ).collect(Collectors.toList()); //coletar esses dados e jogar em episodios
		
		episodios.forEach(System.out::println);		
		
		System.out.println("\n Digite o título do episódio desejado:");		
		var trechoTitulo  = leitura.nextLine();
				
		Optional<Episodio> episodioBuscado = episodios.stream()
			.filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
			.findFirst();	
		
		if (episodioBuscado.isPresent()) {
			System.out.println("Episódio encontrado");
			System.out.println("Temporada: " + episodioBuscado.get().getTemporada());
		}else {
			System.out.println("Episódio não encontrado");
		}
		
		
//		System.out.println("\n A partir de que ano você deseja ver os episódios?");
		
//		var ano = leitura.nextInt();
//		leitura.nextLine();
//		
//		LocalDate dataBusca = LocalDate.of(ano,1,1);
//		
//		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");		
//		
//		episodios.stream()	
//			.filter(e -> e.getDataLancamento() !=null && e.getDataLancamento().isAfter(dataBusca)) //filtrar o episodio onde a data é depois da databusca
//			.forEach(e -> System.out.println( 
//					"Temporada " + e.getTemporada() + 
//					"Episodio " + e.getTitulo() +
//					"Data lançamento " + e.getDataLancamento().format(formatador)				
//				
//			));		
		
		System.out.println("\n Avaliações das temporadas: ");
		
		Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
				.filter(e -> e.getAvaliacao()>0.0)
				.collect(Collectors.groupingBy(Episodio::getTemporada, Collectors.averagingDouble(Episodio::getAvaliacao)));		
		System.out.println(avaliacoesPorTemporada);
		
		
		System.out.println("\n Estatisticas das avaliações dos episodios: ");
		
		DoubleSummaryStatistics est = episodios.stream()
				.filter(e-> e.getAvaliacao() > 0.0)
				.collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
		
		//System.out.println(est);
		System.out.println("Média: " + est.getAverage());
		System.out.println("Melhor episódio: " + est.getMax());
		System.out.println("Pior episódio: " + est.getMin());
		System.out.println("Quantidade: " + est.getCount());
		
				

	}

}
