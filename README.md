# 🍽 GS Witchen - Sistema de Gestão de Restaurante

[Assista ao Pitch 🎥](https://www.youtube.com/watch?v=8fjv8DE_ems) | [~~Aplicação em Nuvem~~ 🌐](http://witchenapp-java.azurewebsites.net/) |  [Assista A Solução](https://youtu.be/IfVl1jmwyD8)
---



## 🔹 Descrição do Projeto

O **GS Witchen** é um sistema web para gerenciamento de pedidos em restaurantes, desenvolvido em **Java Spring Boot** com front-end Thymeleaf.  
O sistema possui **dois fluxos principais**:  

1. **Cozinha:** recebe e processa os pedidos, indicando quando estão prontos.  
2. **Garçom:** adiciona pedidos às comandas, fecha comandas e realiza pagamentos.

O sistema foi desenvolvido por estudantes de **Análise e Desenvolvimento de Sistemas (ADS)** com foco em automação e controle de pedidos.

---

## 🔹 Integrantes

- Eric Issamu de Lima Yoshida  
- Gustavo Matias Texeira  
- Gustavo Monção  

---

## 🔹 Papel da Inteligência Artificial

O sistema utiliza IA para auxiliar na **gestão de pedidos e controle de tempo** de cada prato. A IA ajuda a otimizar a sequência de preparo, oferecendo previsões de tempo de finalização dos pedidos, melhorando a eficiência da cozinha.

---

## 🔹 Tecnologias Utilizadas

- **Java 17**  
- **Spring Boot**  
- **Spring Data JPA**  
- **Thymeleaf**  
- **SQL Server**  
- **Azure App Service**  
- **GitHub Actions** para CI/CD  

---

## 🔹 Funcionalidades do Aplicativo

- Criar comandas para cada mesa.  
- Adicionar pedidos com múltiplos itens.  
- Processamento de pedidos em **fila da cozinha**.  
- Finalizar pedidos e comandas, gerando **pagamento automático**.  
- Visualizar todas as comandas e pedidos em tempo real.  
- **Controle de acesso** com senhas:

| Fluxo     | Senha |
|-----------|-------|
| Cozinha   | 1234  |
| Garcom    | 1234  |

---

## 🔹 Instruções de Uso

1. Acesse a aplicação: [GS Witchen Web App](http://witchenapp-java.azurewebsites.net/).  
2. Faça login:
   - **Cozinha:** senha `1234`  
   - **Garçom:** senha `1234`  
3. Para Garçom:
   - Criar nova comanda para uma mesa.  
   - Adicionar pedidos com itens.  
   - Fechar comandas e registrar pagamentos.  
4. Para Cozinha:
   - Acompanhar a fila de pedidos.  
   - Marcar pedidos como prontos.  
5. Para ambos:
   - Visualizar comandas ativas.  
   - Consultar histórico de pedidos finalizados.  

---

## 🔹 Deploy e Aplicação em Nuvem

O sistema estava publicado no **Azure App Service**, com deploy contínuo via GitHub Actions.  
- Link: [GS Witchen Web App](http://witchenapp-java.azurewebsites.net/)  ❌

---

## 🔹 Vídeo do Pitch

- [Assista ao Pitch 🎥](https://www.youtube.com/watch?v=8fjv8DE_ems)  

---

## 🔹 Observações

- Senhas e acessos são simplificados para fins de demonstração.  
- Este projeto é focado em **demonstração acadêmica e prototipagem**.  
- Para desenvolvimento local, configure o **SQL Server** e variáveis de ambiente conforme instruções do projeto.



