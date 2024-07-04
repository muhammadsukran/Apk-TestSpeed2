const express = require('express');
const http = require('http');
const socketIo = require('socket.io');
const os = require('os');

const app = express();
const server = http.createServer(app);
const io = socketIo(server);

app.use(express.static('public'));

io.on('connection', (socket) => {
    console.log('a user connected');

    // Memperoleh informasi IP address
    const clientIpAddress = socket.handshake.address;

    // Memperoleh informasi MAC address
    const networkInterfaces = os.networkInterfaces();
    let clientMacAddress = '';
    for (const key in networkInterfaces) {
        const iface = networkInterfaces[key].find(iface => !iface.internal && iface.family === 'IPv4');
        if (iface) {
            clientMacAddress = iface.mac;
            break;
        }
    }

    console.log(`Client IP address: ${clientIpAddress}`);
    console.log(`Client MAC address: ${clientMacAddress}`);

    socket.on('disconnect', () => {
        console.log('user disconnected');
    });

    socket.on('screenData', (data) => {
        io.emit('screenData', data);
    });
});

server.listen(3000, () => {
    console.log('listening on *:3000');
});
