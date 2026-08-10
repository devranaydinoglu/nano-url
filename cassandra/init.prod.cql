CREATE KEYSPACE IF NOT EXISTS spring_cassandra
WITH replication = {
    'class': 'NetworkTopologyStrategy',
    'replication_factor': 2
};
