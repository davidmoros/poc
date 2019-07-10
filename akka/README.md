router

La mensajes enviadas a cada sistema Akka son procesados en el propio sistema.
Se aceptan conexiones remotas

remote

Las mensajes enviadas al sistema Akka 1 (RemoteBack1App) no son procesados
Las mensajes enviadas al sistema Akka 2 (RemoteBack2App) son procesados en el sistema Akka 1 (RemoteBack1App)

remote2

Las mensajes enviadas al sistema Akka 1 (RemoteBack1App) son procesados en el sistema Akka 1
Las mensajes enviadas al sistema Akka 2 (RemoteBack2App) son procesados en el sistema Akka 1 (RemoteBack1App)

remote3

Las mensajes enviadas al sistema Akka 1 (RemoteBack1App) no son procesados
Las mensajes enviasas al sistema Akka 2 (RemoteBack2App) son distribuidos y procesados entre el sistema Akka 2 y Akka 1 (RemoteBack1App) 

remote4

Las mensajes enviadas al sistema Akka 1 (RemoteBack1App) son procesados en el sistema Akka 1
Las mensajes enviasas al sistema Akka 2 (RemoteBack2App) son distribuidos y procesados entre el sistema Akka 2 y Akka 1 (RemoteBack1App) 

cluster

Un cluster con un solo nodo (ClusterBackApp)
Las mensajes enviadas al nodo Akka 1 (ClusterBackApp) son procesados en el propio nodo

cluster2

Un cluster con dos nodos, Cluster2Back1App y Cluster2Back2App 
Las mensajes enviadas al nodo Akka 1 (Cluster2Back1App) son distribuidos y procesados entre todos los nodos del cluster

cluster3

Un cluster con dos nodos, Cluster2Back1App y Cluster2Back2App 
Las mensajes enviadas a través de un cliente del cluster, q tiene configurado los dos nodos Back, los mensajes son distribuidos y procesados entre todos los nodos del cluster
