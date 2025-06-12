import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, ComboBox, DatePicker, Dialog, Grid, GridColumn, GridItemModel, TextField, VerticalLayout } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';

import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';

import { useDataProvider } from '@vaadin/hilla-react-crud';
import Album from 'Frontend/generated/org/northpole/workshop/base/models/Album'; 
import { AlbumService } from 'Frontend/generated/endpoints';
import { useEffect } from 'react';

export const config: ViewConfig = {
  title: 'Album',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 2,
    title: 'Album',
  },
};

type AlbumEntryFormProps = {
  onAlbumCreated?: () => void;
};

type AlbumEntryFormUpdateProps = {
  onAlbumUpdated?: () => void;
};

//Album CREATE
function AlbumEntryForm(props: AlbumEntryFormProps) {
  const dialogOpened = useSignal(false);

  const open = () => {
    dialogOpened.value = true;
  };

  const close = () => {
    dialogOpened.value = false;
  };

  const nombre = useSignal('');
  const fecha = useSignal('');
  const Banda = useSignal('');

  const createAlbum = async () => {
      try {
        if (nombre.value.trim().length > 0 && fecha.value.trim().length > 0) {
          const id_banda = parseInt(Banda.value) + 1;
          await AlbumService.createAlbum(nombre.value, fecha.value, id_banda);
          if (props.onAlbumCreated) {
            props.onAlbumCreated();
          }
          nombre.value = '';
          fecha.value = '';
          Banda.value = '';
          dialogOpened.value = false;
          Notification.show('Album creada exitosamente', { duration: 5000, position: 'bottom-end', theme: 'success' });
        } else {
          Notification.show('No se pudo crear, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
        }
  
      } catch (error) {
        console.log(error);
        handleError(error);
      }
    };

    let listaBanda = useSignal<String[]>([]);
        useEffect(() => {
          AlbumService.listBandaCombo().then(data => 
            listaBanda.value = data
          );
        }, []);

  return (
    <>
      <Dialog
        aria-label="Registrar Album"
        draggable
        modeless
        opened={dialogOpened.value}
        onOpenedChanged={(event) => {
          dialogOpened.value = event.detail.value;
        }}
        header={
          <h2
            className="draggable"
            style={{
              flex: 1,
              cursor: 'move',
              margin: 0,
              fontSize: '1.5em',
              fontWeight: 'bold',
              padding: 'var(--lumo-space-m) 0',
            }}
          >
            Registrar Album
          </h2>
        }
        footerRenderer={() => (
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button theme="primary" onClick={createAlbum}>
              Registrar
            </Button>
          </>
        )}
      >
        <VerticalLayout
          theme="spacing"
          style={{ width: '300px', maxWidth: '100%', alignItems: 'stretch' }}
        >
          <VerticalLayout style={{ alignItems: 'stretch' }}>
            <TextField label="Nombre"
              placeholder='Ingrese el nombre de la Album'
              aria-label='Ingrese el nombre de la Album'
              value={nombre.value}
              onValueChanged={(evt) => (nombre.value = evt.detail.value)}
            />
            <DatePicker
              label="Fecha de creacion"
              placeholder="Seleccione una fecha"
              aria-label="Seleccione una fecha"
              value={fecha.value}
              onValueChanged={(evt) => (fecha.value = evt.detail.value)}
            />
            <ComboBox label ="Banda"
              items={listaBanda.value}
              placeholder='Seleccione la Banda'
              aria-label='Seleccione la Banda'
              value={Banda.value}
              onValueChanged={(evt) => (Banda.value = evt.detail.value)}
            />
          </VerticalLayout>
        </VerticalLayout>
      </Dialog>
      <Button onClick={open}>Registrar</Button>
    </>
  );
}

//UPDATE Album
function AlbumEntryFormUpdate(props: AlbumEntryFormUpdateProps) {
  //console.log(props);
  const dialogOpened = useSignal(false);

  const open = () => {
    dialogOpened.value = true;
  };

  const close = () => {
    dialogOpened.value = false;
  };

  const nombre = useSignal(props.arguments.nombre);
  const fecha = useSignal(props.arguments.fecha);
  const ident = useSignal(props.arguments.id);
  
  const updateAlbum = async () => {
      try {
        if (nombre.value.trim().length > 0 && fecha.value.trim().length > 0) {
          console.log(parseInt(ident.value)+" *********");
          await AlbumService.updateAlbum(parseInt(ident.value), nombre.value, fecha.value);
          if (props.onAlbumUpdated) {
            props.onAlbumUpdated();
          }
          nombre.value = '';
          fecha.value = '';
          dialogOpened.value = false;
          Notification.show('Album creada exitosamente', { duration: 5000, position: 'bottom-end', theme: 'success' });
        } else {
          Notification.show('No se pudo crear, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
        }
  
      } catch (error) {
        console.log(error);
        handleError(error);
      }
    };

  return (
    <>
      <Dialog
        aria-label="Editar Album"
        draggable
        modeless
        opened={dialogOpened.value}
        onOpenedChanged={(event) => {
          dialogOpened.value = event.detail.value;
        }}
        header={
          <h2
            className="draggable"
            style={{
              flex: 1,
              cursor: 'move',
              margin: 0,
              fontSize: '1.5em',
              fontWeight: 'bold',
              padding: 'var(--lumo-space-m) 0',
            }}
          >
            Editar Album
          </h2>
        }
        footerRenderer={() => (
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button theme="primary" onClick={updateAlbum}>
              Actualizar
            </Button>
          </>
        )}
      >
        <VerticalLayout
          theme="spacing"
          style={{ width: '300px', maxWidth: '100%', alignItems: 'stretch' }}
        >
          <VerticalLayout style={{ alignItems: 'stretch' }}>
            <TextField label="Nombre"
              placeholder='Ingrese el nombre de la Album'
              aria-label='Ingrese el nombre de la Album'
              value={nombre.value}
              onValueChanged={(evt) => (nombre.value = evt.detail.value)}
            />
            <DatePicker
              label="Fecha de creacion"
              placeholder="Seleccione una fecha"
              aria-label="Seleccione una fecha"
              value={fecha.value}
              onValueChanged={(evt) => (fecha.value = evt.detail.value)}
            />
          </VerticalLayout>
        </VerticalLayout>
      </Dialog>
      <Button onClick={open}>Editar</Button>
    </>
  );
}
//***************************

const dateFormatter = new Intl.DateTimeFormat('es-ES', {
  dateStyle: 'medium',
  timeStyle: 'short'
});




function index({ model }: { model: GridItemModel<Album> }) {
  return (
    <span>
      {model.index + 1}
    </span>
  );
}

export default function AlbumView() {
  const dataProvider = useDataProvider<Album>({
    list: () => AlbumService.listAlbum(),
  });

  function link({ item }: { item: Album }) {
  return (
    <span>
      <AlbumEntryFormUpdate arguments={item}  onAlbumUpdated={dataProvider.refresh}/>
    </span>
  );
}

  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Albums">
        <Group>
          <AlbumEntryForm onAlbumCreated={dataProvider.refresh} />
        </Group>
      </ViewToolbar>
      <Grid dataProvider={dataProvider.dataProvider}>
        <GridColumn header="Nro" renderer={index} />
        <GridColumn path="nombre" header="Nombre del Album" />
        <GridColumn path="Banda" header="Banda" />
        <GridColumn path="fecha" header="Fecha">
          {({ item }) => (item.fecha ? dateFormatter.format(new Date(item.fecha)) : 'Never')}
        </GridColumn>

        <GridColumn header="Acciones" renderer={link} />
      </Grid>
    </main>
  );
}

