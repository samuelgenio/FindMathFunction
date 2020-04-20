# FindMathFunction

Aplicação que através de algoritmo genético tenta descobrir uma função matemática.

# Como funciona?

Através de uma programação genética, onde cada elemento da função é representada por um **Nó** de uma árvore, é feito a seleção das expressões que mais se aproximam o resultado esperado.

![](https://raw.githubusercontent.com/samuelgenio/FindMathFunction/master/files/diagrama.jpeg)

# Solução

Para executar a aplicação é necessário informar:

* Quantidade de indivíduos serão trabalhados;
* Quantidade de gerações a serem executadas;
* Percentual da população a sofrer mutação;

No gráfico é possível visualizar com a curva de aprendizado, o quanto a função gerada se parece com a função alvo. Ao lado do gráfico é mostrado, através do atributo **Fitness** a expressão que mais se aproxima do resultado esperado por geração.

![](https://raw.githubusercontent.com/samuelgenio/FindMathFunction/master/files/imagem.png)
