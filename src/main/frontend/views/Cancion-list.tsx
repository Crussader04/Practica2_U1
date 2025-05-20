import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, ComboBox, DatePicker, Dialog, Grid, GridColumn, GridItemModel, NumberField, TextField, VerticalLayout } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';

import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';

import { useDataProvider } from '@vaadin/hilla-react-crud';
import Cancion from 'Frontend/generated/org/northpole/workshop/base/models/Cancion'; 
import { CancionService } from 'Frontend/generated/endpoints';
import { useEffect } from 'react';

export const config: ViewConfig = {
  title: 'Cancion',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 2,
    title: 'Cancion',
  },
};

type CancionEntryFormProps = {
  onCancionCreated?: () => void;
};

type CancionEntryFormUpdateProps = {
  onCancionUpdated?: () => void;
};

//Cancion CREATE
function CancionEntryForm(props: CancionEntryFormProps) {
const dialogOpened = useSignal(false);

  const open = () => {
    dialogOpened.value = true;
  };

  const close = () => {
    dialogOpened.value = false;
  };


  const nombre = useSignal('');
  const Album = useSignal('');
  const Genero = useSignal('');
  const Url = useSignal('');
  const Duracion = useSignal('');
  const Tipo = useSignal('');

  const createCancion = async () => {
      try {
        if (nombre.value.trim().length > 0 && Album.value.trim().length > 0 && Genero.value.trim().length > 0 && Url.value.trim().length > 0 && Duracion.value.trim().length > 0 && Tipo.value.trim().length > 0) {
          const id_genero = parseInt(Genero.value)+ 1;
          const id_album = parseInt(Album.value)+ 1;
          await CancionService.createCancion(nombre.value, parseInt(Duracion.value), Url.value, Tipo.value, id_genero, id_album);
          if (props.onCancionCreated) {
            props.onCancionCreated();
          }
          nombre.value = '';
          Genero.value = '';
          Album.value = '';
          Url.value = '';
          Duracion.value = '';
          Tipo.value = '';
          dialogOpened.value = false;
          Notification.show('Cancion creada exitosamente', { duration: 5000, position: 'bottom-end', theme: 'success' });
        } else {
          Notification.show('No se pudo crear, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
        }
  
      } catch (error) {
        console.log(error);
        handleError(error);
      }
    };

    let listaGenero = useSignal<String[]>([]);
    useEffect(() => {
      CancionService.listGeneroCombo().then(data => 
        listaGenero.value = data
      );
    }, []);

    let listaAlbum = useSignal<String[]>([]);
    useEffect(() => {
      CancionService.listAlbumCombo().then(data => 
        listaAlbum.value = data
      );
    }, []);
    let listaTipo = useSignal<String[]>([]);
    useEffect(() => {
      CancionService.listTipo().then(data =>
        listaTipo.value = data
      );
    }, []);
/*     const dialogOpened = useSignal(false); */
  return (
    <>
      <Dialog
        aria-label="Registrar Cancion"
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
            Registrar Cancion
          </h2>
        }
        footerRenderer={() => (
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button theme="primary" onClick={createCancion}>
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
              placeholder='Ingrese el nombre de la Cancion'
              aria-label='nombre de la Cancion'
              value={nombre.value}
              onValueChanged={(evt) => (nombre.value = evt.detail.value)}
            />
            <ComboBox label="Album"
              items={listaAlbum.value}
              placeholder="Seleccione un Album"
              aria-label="Seleccione un Album"
              value={Album.value}
              onValueChanged={(evt) => (Album.value = evt.detail.value)}
            />
            <ComboBox label="Genero"
              items={listaGenero.value}
              placeholder="Seleccione un genero de la lista"
              aria-label="Seleccione un genero"
              value={Genero.value}
              onValueChanged={(evt) => (Genero.value = evt.detail.value)}
            />
            <TextField label="Url"
              placeholder='Ingrese el link de la Cancion'
              aria-label='Ingrese Url'
              value={Url.value}
              onValueChanged={(evt) => (Url.value = evt.detail.value)}
            />
            <NumberField label="Duracion"
              placeholder='Ingrese la Duracion de la Cancion'
              aria-label='Duracion de la Cancion'
              value={Duracion.value}
              onValueChanged={(evt) => (Duracion.value = evt.detail.value)}
            />
            <ComboBox label="Tipo"
              items={listaTipo.value}
              placeholder="Seleccione un Tipo de la lista"
              aria-label="Seleccione un Tipo"
              value={Tipo.value}
              onValueChanged={(evt) => (Tipo.value = evt.detail.value)}
            />
          </VerticalLayout>
        </VerticalLayout>
      </Dialog>
      <Button onClick={open}>Registrar</Button>
    </>
  );
}

//UPDATE Cancion
function CancionEntryFormUpdate(props: CancionEntryFormUpdateProps) {
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
  
  const updateCancion = async () => {
      try {
        if (nombre.value.trim().length > 0 && fecha.value.trim().length > 0) {
          console.log(parseInt(ident.value)+" *********");
          await CancionService.updateCancion(parseInt(ident.value), nombre.value, fecha.value);
          if (props.onCancionUpdated) {
            props.onCancionUpdated();
          }
          nombre.value = '';
          fecha.value = '';
          dialogOpened.value = false;
          Notification.show('Cancion creada exitosamente', { duration: 5000, position: 'bottom-end', theme: 'success' });
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
        aria-label="Editar Cancion"
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
            Editar Cancion
          </h2>
        }
        footerRenderer={() => (
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button theme="primary" onClick={updateCancion}>
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
              placeholder='Ingrese el nombre de la Cancion'
              aria-label='Ingrese el nombre de la Cancion'
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
//*************************** */

const dateFormatter = new Intl.DateTimeFormat(undefined, {
  dateStyle: 'medium',
});



function index({ model }: { model: GridItemModel<Cancion> }) {
  return (
    <span>
      {model.index + 1}
    </span>
  );
}

export default function CancionView() {

  const dataProvider = useDataProvider<Cancion>({
    list: () => CancionService.listCancion(),
  });

  function link({ item }: { item: Cancion }) {
  return (
    <span>
      <CancionEntryFormUpdate arguments={item}  onCancionUpdated={dataProvider.refresh}/>
    </span>
  );
}

  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Cancions">
        <Group>
          <CancionEntryForm onCancionCreated={dataProvider.refresh} />
        </Group>
      </ViewToolbar>
      <Grid dataProvider={dataProvider.dataProvider}>
        <GridColumn header="Nro" renderer={index} />
        <GridColumn path="nombre" header="Nombre de la cancion" />
        <GridColumn path="album" header="Album" />
        <GridColumn path="genero" header="Genero" />
        <GridColumn path="url" header="Url" />
        <GridColumn path="duracion" header="Duracion" />
        <GridColumn path="tipo" header="tipo de archivo" />
        <GridColumn header="Acciones" renderer={link} />
      </Grid>
    </main>
  );
}

