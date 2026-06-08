O Hospital Central de Maputo é uma das maiores instituições de saúde do país, servindo uma população extensa e heterogénea. A gestão eficiente dos dados dos doentes – desde o registo inicial até ao histórico de consultas, exames e tratamentos – é crítica para a qualidade do serviço prestado e para a tomada de decisão médica. O Minimundo fornecido descreve essa realidade e foi o ponto de partida para o presente trabalho.

Da análise do Minimundo, foram identificadas as seguintes entidades principais:

- ``Paciente`` – identificado por um ID único, com dados pessoais como nome, data de nascimento, género, endereço e dois contactos: o pessoal e o de emergência. Um doente pode ter várias consultas, exames, internamentos e tratamentos ao longo do tempo.
- ``Profissional`` de Saúde – engloba médicos e enfermeiros, cada um com ID único, número de cédula profissional, departamento, género e contactos. Os médicos têm adicionalmente uma especialidade.
-	``Departamento`` – estrutura organizacional que agrupa profissionais de saúde por área clínica (Cardiologia, Pediatria, Cirurgia, Urgência, entre outros).
-	``Consulta`` – registo de um atendimento médico, com data, hora, tipo, observações e estado, associado a um doente e a um médico.
-	``Diagnóstico`` – gerado na sequência de uma consulta e de um exame, com código e observações clínicas.
-	``Exame`` – solicitação de análise laboratorial ou radiológica, com tipo, resultado e observações, vinculada a um doente.
-	``Internamento`` – período de hospitalização com indicação de cama, quarto e motivo, associado a um doente e supervisionado por enfermeiros.
-	``Tratamento`` – intervenção clínica prescrita após diagnóstico, com tipo e observações.
-	``Medicamento`` – substância farmacológica administrada no contexto de um tratamento, com código, nome e dosagem.

Quanto aos relacionamentos, o Minimundo define que um doente pode acumular várias consultas, internamentos, exames e tratamentos; que cada consulta dá origem a um diagnóstico; que os internamentos e tratamentos decorrem do diagnóstico; e que os medicamentos são administrados no contexto dos tratamentos.
