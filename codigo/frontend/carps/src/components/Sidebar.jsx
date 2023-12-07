/**
 * @file Sidebar.jsx
 * @description Componente funcional que representa uma barra lateral persistente.
 */

import * as React from 'react';
import { Link } from 'react-router-dom';
import '../styles/sidebar.css';
import { styled, useTheme } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Drawer from '@mui/material/Drawer';
import MuiAppBar from '@mui/material/AppBar';
import List from '@mui/material/List';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import Typography from '@mui/material/Typography';
import Avatar from '@mui/material/Avatar';
import PersonIcon from '@mui/icons-material/Person';

// Largura padrão da barra lateral
const larguraDaSidebar = 240;

/**
 * Estiliza o cabeçalho da sidebar.
 * @type {React.FC}
 */
const CabecalhoDaSidebar = styled('div')(({ theme }) => ({
    display: 'flex',
    alignItems: 'center',
    margin: theme.spacing(1),
    justifyContent: 'flex-end',
}));

/**
 * Estiliza a barra de ferramentas com base no estado 'open'.
 * @type {React.FC}
 */
const BarraDeFerramentasPersonalizada = styled(MuiAppBar)(({ theme, open }) => ({
    zIndex: theme.zIndex.drawer + 1,
    transition: theme.transitions.create(['width', 'margin'], {
        duration: theme.transitions.duration.leavingScreen,
    }),
    marginLeft: open ? larguraDaSidebar : 0,
    background: open ? theme.palette.primary.main : 'transparent',
    boxShadow: open ? theme.shadows[0] : 'none',
}));

/**
 * Estiliza o botão de ícone usado para abrir a sidebar.
 * @type {React.FC}
 */
const BotaoIconButtonPersonalizado = styled(IconButton)(({ theme, open }) => ({
    margin: theme.spacing(1),
    position: 'absolute',
    left: open ? larguraDaSidebar : 0,
    zIndex: theme.zIndex.drawer - 1,
    backgroundColor: open ? theme.palette.primary.main : 'transparent',
    display: open ? 'none' : 'block',
    '&:hover': {
        backgroundColor: 'transparent',
    },
}));

/**
 * Estiliza o componente "Sair", ao clicar finaliza a sessão.
 * @type {React.FC}
 */
const SairComponent = styled(Box)(({ theme, open }) => ({
    margin: '0.9em 4em',
    textAlign: 'right',
    width: '100%',
    position: 'absolute',
    right: open ? theme.spacing(1) + larguraDaSidebar : theme.spacing(1),
}));

/**
 * Manipula o clique nos itens da barra lateral.
 * @param {number} indice - Índice do item clicado.
 * @returns {void}
 */
const lidarComCliqueItem = (indice) => {
    if (indice === 0) {
        // Lidar com o clique em "Política de Privacidade" (por exemplo, baixar PDF)
        const linkDownload = document.createElement('a');
        linkDownload.href = '../assets/politica_de_privacidade.pdf';
        linkDownload.download = 'politica_de_privacidade.pdf';
        linkDownload.click();
    } else if (indice === 1) {
        // Lidar com o clique em "Entre em Contato" (por exemplo, redirecionar para outra página)
        window.location.href = '/contato';
    }
};

/**
 * Componente funcional principal da barra lateral persistente.
 * @returns {React.FC}
 */
export default function SidebarEsquerda() {
    const theme = useTheme();
    const [aberto, setAberto] = React.useState(false);

    /**
     * Manipula a abertura da sidebar.
     * @returns {void}
     */
    const lidarComAberturaDaSidebar = () => {
        setAberto(true);
    };

    /**
     * Manipula o fechamento da sidebar.
     * @returns {void}
     */
    const lidarComFechamentoDaSidebar = () => {
        setAberto(false);
    };

    // Renderiza a estrutura da barra lateral
    return (
        <Box sx={{ display: 'flex', fontFamily: 'Montserrat' }}>
            {/* Componente de finalizar sessão */}
            <SairComponent>
                <Link to="/" style={{ textDecoration: 'none', color: 'rgba(0, 0, 0, 0.54)' }}>
                    <Typography sx={{ marginTop: '0.1em', fontSize: 16, fontFamily: 'Montserrat', fontWeight: 600 }}>
                        Sair
                    </Typography>
                </Link>
            </SairComponent>
    
            {/* Barra de ferramentas personalizada */}
            <BarraDeFerramentasPersonalizada open={aberto}>
            </BarraDeFerramentasPersonalizada>
    
            {/* Botão de ícone personalizado para abrir a barra lateral */}
            <BotaoIconButtonPersonalizado
                color="inherit"
                aria-label="open sidebar"
                onClick={lidarComAberturaDaSidebar}
                edge="start"
                style={{ color: 'rgba(0, 0, 0, 0.54)' }}
            >
                <MenuIcon />
            </BotaoIconButtonPersonalizado>
    
            {/* Box para branding */}
            <Box sx={{ margin: '0.8em 4em', textAlign: 'center' }}>
                <Typography sx={{ fontSize: 20, color: 'rgba(0, 0, 0, 0.54)', fontFamily: 'Montserrat', fontWeight: 550 }}>
                    Carps
                </Typography>
            </Box>
    
            {/* Gaveta (Drawer) da barra lateral */}
            <Drawer
                sx={{
                    width: larguraDaSidebar,
                    flexShrink: 0,
                    '& .MuiDrawer-paper': {
                        width: larguraDaSidebar,
                        boxSizing: 'border-box',
                    },
                }}
                variant="persistent"
                anchor="left"
                open={aberto}
            >
                {/* Cabeçalho da barra lateral */}
                <CabecalhoDaSidebar>
                    <IconButton onClick={lidarComFechamentoDaSidebar}>
                        {theme.direction === 'ltr' ? <ChevronLeftIcon /> : <ChevronRightIcon />}
                    </IconButton>
                </CabecalhoDaSidebar>
    
                {/* Informações do usuário e avatar */}
                <Box sx={{ padding: '2em', textAlign: 'center' }}>
                    <Avatar sx={{ width: 64, height: 64, margin: '0 auto', marginBottom: '10px', backgroundColor: '#3f51b5' }}>
                        <PersonIcon sx={{ fontSize: 40 }} />
                    </Avatar>
                    <Typography sx={{ fontSize: 16, color: '#212121', fontFamily: 'Montserrat', fontWeight: 600, padding: '3px' }}>
                        Maria Burle
                    </Typography>
                    <Typography sx={{ fontSize: 14, color: '#919191', fontFamily: 'Montserrat', fontWeight: 500, padding: '3px' }}>
                        maria.burle@vtal.com
                    </Typography>
                </Box>
    
                {/* Lista de itens na barra lateral */}
                <List sx={{ marginTop: 'auto' }}>
                    {['Política de privacidade', 'Entre em contato'].map((texto, indice) => (
                        <ListItem key={texto} disablePadding>
                            <ListItemButton
                                sx={{
                                    marginX: 2,
                                    borderRadius: '15px',
                                    '&:hover': {
                                        backgroundColor: '#EAEAEA',
                                    },
                                }}
                                onClick={() => lidarComCliqueItem(indice)}
                            >
                                <ListItemText primary={texto} primaryTypographyProps={{ style: { fontSize: 14, fontFamily: 'Montserrat', fontWeight: 500 } }} />
                            </ListItemButton>
                        </ListItem>
                    ))}
                </List>
    
                {/* Rodapé da barra lateral */}
                <Box sx={{ padding: '2em', textAlign: 'center' }}>
                    <Typography sx={{ fontSize: 11, color: '#616161', fontFamily: 'Montserrat' }}>
                        © Consilium 2023
                    </Typography>
                </Box>
            </Drawer>
        </Box>
    );    
}
