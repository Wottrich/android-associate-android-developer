# Estudos para o certificado de Desenvolvedor Android Associado (pt-br)
Um projeto para estudar topicos sobre o certificado de Desenvolvedor Android Associado.

### Tópicos de estudo baseado na página [Study guide: Associate Android Developer Certification](https://developers.google.com/certification/associate-android-developer/study-guide)
- Android core
- User interface
- Data management
- Debugging
- Testing

# Projeto
O projeto desenvolvido será um app baseado, mas com customizações, em uma técnica conhecida como [Pomodoro](https://brasilescola.uol.com.br/dicas-de-estudo/tecnica-pomodoro-que-e-e-como-funciona.htm) e será chamado de `PomodoroUniverse`.

# Tópicos [WIP]
Cada tópico contem outros subtópicos que serão adicionados conforme o estudo sobre eles avançam.

# Android Core

## Arquitetura da plataforma Android
O Android é uma pilha de software com base em Linux de código aberto. 

O diagrama a seguir mostra a maioria dos componentes da plataforma Android:

<img src="https://user-images.githubusercontent.com/24254062/212899156-45e40009-92de-49b2-8bf6-04ac4a8c3f3e.png" alt="Pilha de software do Android" width="500px">

Informações coletadas em: https://developer.android.com/guide/platform?hl=pt-br

### Camada de abstração de hardware (HAL)

A HAL fornece interfaces padrões que expõem as capacidades de hardware do dipositivo para a estrutura da Java API de maior nível. Quando um Framework API faz uma chamada para acessar o hardware do dispositivo, o sistema Android carrega o módulo da biblioteca para esse componente de hardware.

Informações coletadas em: https://developer.android.com/guide/platform?hl=pt-br#hal

Alguns tipos de HAL: https://source.android.com/devices/architecture/hal-types?hl=pt-br

### Android Runtime
Para dispositivos Android versão 5.0 (API 21) ou mais recentes, cada aplicativo executa o próprio processo com a instãncia própria do [Android Runtime (ART)](https://source.android.com/devices/tech/dalvik/index.html?hl=pt-br). O ART é projetado para executar várias máquinas virtuais em dispositivos de baixa memória executando arquivos DEX, um formato de bytecode projetado especialment para Android.

Alguns dos recursos principais de ART são:
- Compilação "ahead-of-time" (AOT) e "just-in-time" (JIT)
- Coleta de lixo (GC) otimizada
- No Android 9 (API 28) ou superior, a conversão dos arquivo de formato DEX de um pacote de aplicativos usa o código de máquina mais compacto.
- Melhor compatibilidade de depuração, inclusive um criador de perfil de exemplo, exceções de diagnóstico detalhadas e geração de relatórios de erros, além de capacidade de definir pontos de controle para monitorar campos específicos.

Informações coletadas em: https://developer.android.com/guide/platform?hl=pt-br#art

### Bibliotecas C/C++ nativas

Vários componentes e serviços principais do sistema Android, como ART e HAL, são implementados por código nativo que exige bibliotecas nativas programadas em C e C++. A plataforma Android fornece as Java Framework APIs para expor a funcionalidade de algumas dessas bibliotecas nativas aos aplicativos.

Informações coletadas em: https://developer.android.com/guide/platform?hl=pt-br#native-libs

### Estrutura Java API

 O conjunto completo de recursos do SO Android está disponível pelas APIs programadas na linguagem Java. Essas APIs formam os blocos de programação que você precisa para criar os aplicativos Android simplificando a reutilização de componentes e serviços de sistema modulares e principais, inclusive:

- Um sistema de visualização rico e extensivo útil para programar a IU de um aplicativo, com listas, grades, caixas de texto, botões e até mesmo um navegador da web incorporado
- Um gerenciador de recursos, fornecendo acesso a recursos sem código como strings localizadas, gráficos e arquivos de layout
- Um gerenciador de notificação que permite que todos os aplicativos exibam alertas personalizados na barra de status
- Um gerenciador de atividade que gerencia o ciclo de vida dos aplicativos e fornece uma pilha de navegação inversa
- Provedores de conteúdo que permite que aplicativos acessem dados de outros aplicativos, como o aplicativo Contatos, ou compartilhem os próprios dados

Os desenvolvedores têm acesso completo às mesmas Framework APIs que os aplicativos do sistema Android usam. 

informações coletadas em: https://developer.android.com/guide/platform?hl=pt-br#api-framework

# User interface
# Data management
# Debugging
# Testing
