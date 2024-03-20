insert into project(name, budget) VALUES('Project Alpha',	50000.00);
insert into project(name, budget) VALUES('Project Beta',	100000.00);
insert into project(name, budget) VALUES('Project Gamma',	150000.00);
insert into project(name, budget) VALUES('Project Delta',	75000.00);


insert into researcher(name, specialization) VALUES('Marie Curie',	'Radioactivity');
insert into researcher(name, specialization) VALUES('Albert Einstein',	'Relativity');
insert into researcher(name, specialization) VALUES('Isaac Newton',	'Classical Mechanics');
insert into researcher(name, specialization) VALUES('Niels Bohr',	'Quantum Mechanics');

insert into researcher_project(researcherId, projectId) VALUES(1, 1);
insert into researcher_project(researcherId, projectId) VALUES(1, 2);
insert into researcher_project(researcherId, projectId) VALUES(2, 2);
insert into researcher_project(researcherId, projectId) VALUES(3, 3);
insert into researcher_project(researcherId, projectId) VALUES(3, 4);
insert into researcher_project(researcherId, projectId) VALUES(4, 4);
